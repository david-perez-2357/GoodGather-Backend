package main.goodgatherbackend.security.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import main.goodgatherbackend.dtos.UserClientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        authenticationService.register(request);
        return ResponseEntity.ok("Registro exitoso");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
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

        return ResponseEntity.ok("Inicio de sesión exitoso");
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
}
