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

    @GetMapping("/user")
    public ResponseEntity<UserClientDTO> getUser(HttpServletRequest response) {
        String jwt = authenticationService.getJwtFromCookies(response);
        if (jwt == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(authenticationService.getCurrentUser(jwt));
    }

    @GetMapping("/ckeck-username-exists")
    public Boolean checkUsername (@RequestParam String username){
        return userRepository.existsByUsername(username);
    }

    @GetMapping("/ckeck-email-exists")
    public Boolean checkEmail (@RequestParam String email){
        return clientRepository.existsClientsByEmail(email);
    }

}
