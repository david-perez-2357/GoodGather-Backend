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

    /**
     * Registra un nuevo usuario en el sistema.
     * Este método toma un objeto de solicitud de registro, crea un nuevo usuario con las credenciales proporcionadas,
     * lo guarda en la base de datos y genera un token JWT para el usuario registrado.
     * @param request Objeto `RegisterRequest` que contiene el nombre de usuario y la contraseña.
     * @return Objeto `AuthenticationResponse` con el token JWT generado.
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

    /**
     * Autentica a un usuario utilizando sus credenciales.
     * Este método verifica las credenciales proporcionadas, valida la autenticación,
     * genera un token JWT para el usuario autenticado y lo devuelve como parte de la respuesta.
     * @param request Objeto `AuthenticationRequest` que contiene el nombre de usuario y la contraseña.
     * @return Objeto `AuthenticationResponse` con el token JWT generado.
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


    /**
     * Extrae el token JWT de las cookies de una solicitud HTTP.
     * Este método recorre las cookies de la solicitud buscando una llamada "jwt" y devuelve su valor.
     * Si no se encuentra la cookie, devuelve `null`.
     * @param request Objeto `HttpServletRequest` que contiene las cookies de la solicitud.
     * @return El valor del token JWT si se encuentra, o `null` si no está presente.
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

    /**
     * Obtiene la información del usuario actual a partir de un token JWT.
     * Este método extrae el nombre de usuario del token JWT, recupera al usuario correspondiente de la base de datos,
     * busca la información asociada del cliente y la convierte en un objeto DTO para devolverla
     * @param jwt Token JWT del usuario.
     * @return Objeto `UserClientDTO` que contiene la información del usuario actual.
     */
    public UserClientDTO getCurrentUser(String jwt) {
        String username = jwtService.extractUsername(jwt);
        User user = userRepository.findByUsername(username).orElseThrow();
        Client client = clientRepository.findByUser(user).orElseThrow();
        return userClientMapper.toDTO(client);
    }
}
