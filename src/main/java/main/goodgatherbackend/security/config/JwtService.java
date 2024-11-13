package main.goodgatherbackend.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "28379222a35adeb9bff03c9f8ba2fabb7b0b81fbced0c4ed69eb3560b3465fdff62d694947ea1c74d84e055c04d014d7c4cfa1a080bdd6b7b1b2d23e3ee65eb100c736e9440b2d56074a9cfb5e134617089dca6797a7b96065bc8dac8ef3c038e381c41e73a50853db00c3986722a635ae2654ecf2eca1570b350e3cee31d2638f5cddcf4023213a9b2d46d53ebcc904e28e2320dbda992935b773ca22dc35699d9def3444e4a41bde2f58e56c0d41dfd70a1786c6e931594bd9d46487af3393e3ebab942cf8a18c8d64aa6ace40d5d4e920ae1398f2c70f52ee011a069326615ad08d463ec5a77294bc3091b5aa5118deb249d8eb75cc3b3287f7c490372f69";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
