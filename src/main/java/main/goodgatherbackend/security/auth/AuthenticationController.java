package main.goodgatherbackend.security.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserClientDTO userClientDTO) {

        try {
            clientService.checkEmailExist(userClientDTO);
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername(userClientDTO.getUsername());
            registerRequest.setPassword(userClientDTO.getPassword());
            authenticationService.register(registerRequest);

            clientService.saveClient(userClientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User and client created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user or client: " + e.getMessage());

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
}
