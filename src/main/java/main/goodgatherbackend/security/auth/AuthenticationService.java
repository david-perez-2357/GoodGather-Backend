package main.goodgatherbackend.security.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import main.goodgatherbackend.repositories.ClientRepository;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.mappers.UserClientMapper;
import main.goodgatherbackend.models.Client;
import main.goodgatherbackend.repositories.ClientRepository;
import main.goodgatherbackend.security.config.JwtAuthenticationFilter;
import main.goodgatherbackend.security.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import main.goodgatherbackend.repositories.UserRepository;
import main.goodgatherbackend.models.User;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserClientMapper userClientMapper;
    private final ClientRepository clientRepository;

    /**@
     * Recibe un RegisterRequest y construye una entidad User.
     * Codifica la contraseña antes de guardarla en la base de datos.
     * Genera un token JWT para el usuario recién registrado.
     * @param request
     * @return
     */

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    /**@
     * Autentica al usuario usando AuthenticationManager.
     * Genera y devuelve un token JWT.
     * @param request
     * @return
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    /**@
     * Extrae el token JWT de las cookies de una solicitud HTTP.
     * @param request
     * @return
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**@
     * Extrae el nombre de usuario del token JWT.
     * Busca el usuario y su cliente asociado, y los convierte a un DTO.
     * @param jwt
     * @return
     */

    public UserClientDTO getCurrentUser(String jwt) {
        String username = jwtService.extractUsername(jwt);
        User user = userRepository.findByUsername(username).orElseThrow();
        Client client = clientRepository.findByUser(user).orElseThrow();
        return userClientMapper.toDTO(client);
    }
}
