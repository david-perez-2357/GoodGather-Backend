package main.goodgatherbackend.security.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import main.goodgatherbackend.controllers.ClientController;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.repositories.ClientRepository;
import main.goodgatherbackend.repositories.UserRepository;
import main.goodgatherbackend.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private ClientService clientService;
    private ClientController clientController;
    private final UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Registra un nuevo usuario y guarda la información del cliente.
     * Este método toma los datos de un usuario y cliente proporcionados en el cuerpo de la solicitud,
     * registra al usuario mediante el servicio de autenticación y guarda al cliente en la base de datos.
     * Si ocurre un error durante el registro, se devuelve un estado de error interno.
     * @param userClientDTO Objeto que contiene la información del usuario y del cliente.
     * @return Respuesta HTTP con el objeto `UserClientDTO` registrado en caso de éxito, o un estado 500 en caso de error.
     */
    @PostMapping("/register")
    public ResponseEntity<UserClientDTO> createUser(@RequestBody UserClientDTO userClientDTO) {

        try {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername(userClientDTO.getUsername());
            registerRequest.setPassword(userClientDTO.getPassword());
            authenticationService.register(registerRequest);
            clientService.saveClient(userClientDTO);
            return ResponseEntity.ok(userClientDTO);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while registering user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Autentica a un usuario y genera un token JWT.
     * Este método valida las credenciales del usuario, genera un token JWT y lo almacena
     * como una cookie segura en la respuesta. Devuelve la solicitud de autenticación
     * original en caso de éxito.
     * @param request Objeto `AuthenticationRequest` con las credenciales del usuario.
     * @param response Objeto `HttpServletResponse` para agregar la cookie con el token JWT.
     * @return Respuesta HTTP con el objeto `AuthenticationRequest` en caso de éxito.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationRequest> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        String jwtToken = String.valueOf(authenticationService.authenticate(request).getToken());
        System.out.println(jwtToken);

        // Crear cookie con el token JWT
        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60); // 24 horas

        // Agregar la cookie a la respuesta
        response.addCookie(jwtCookie);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(request.getUsername());
        authenticationRequest.setPassword(request.getPassword());


        return ResponseEntity.ok(authenticationRequest);



    }

    /**
     * Cierra la sesión del usuario.
     * Este método elimina la cookie JWT de la respuesta para cerrar la sesión del usuario.
     * @param response Objeto `HttpServletResponse` para eliminar la cookie JWT.
     * @return Respuesta HTTP indicando que el cierre de sesión fue exitoso.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Eliminar cookie

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Logout exitoso");
    }


    /**
     * Obtiene la información del usuario actual.
     * Este método recupera el token JWT de las cookies, valida su existencia y devuelve
     * la información del usuario asociado al token. Si el token no está presente, devuelve
     * un estado HTTP 403 (prohibido).
     * @param response Objeto `HttpServletRequest` para obtener las cookies de la solicitud.
     * @return Respuesta HTTP con el objeto `UserClientDTO` del usuario actual o estado 403 si no se encuentra el token.
     */
    @GetMapping("/user")
    public ResponseEntity<UserClientDTO> getUser(HttpServletRequest response) {
        String jwt = authenticationService.getJwtFromCookies(response);
        if (jwt == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(authenticationService.getCurrentUser(jwt));
    }

    /**
     * Verifica si un nombre de usuario ya existe en la base de datos.
     * @param username Nombre de usuario a verificar.
     * @return `true` si el nombre de usuario ya existe, `false` en caso contrario.
     */
     @GetMapping("/ckeck-username-exists")
    public Boolean checkUsername (@RequestParam String username){
        return userRepository.existsByUsername(username);
    }

    /**
     * Verifica si un correo electrónico ya existe en la base de datos.
     * @param email Correo electrónico a verificar.
     * @return `true` si el correo electrónico ya existe, `false` en caso contrario.
     */
      @GetMapping("/ckeck-email-exists")
    public Boolean checkEmail (@RequestParam String email){
        return clientRepository.existsClientsByEmail(email);
    }

}
