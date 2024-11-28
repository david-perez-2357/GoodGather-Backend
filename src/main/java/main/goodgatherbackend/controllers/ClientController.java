package main.goodgatherbackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.ClientDTO;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.security.auth.AuthenticationRequest;
import main.goodgatherbackend.security.auth.AuthenticationResponse;
import main.goodgatherbackend.security.auth.AuthenticationService;
import main.goodgatherbackend.security.auth.RegisterRequest;
import main.goodgatherbackend.services.ClientService;
import main.goodgatherbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private ClientService clientService;

    @GetMapping()
    public List<ClientDTO> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public UserClientDTO getById(@PathVariable Integer id) {
        return clientService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserClientDTO userClientDTO) {

        try {
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

//    @PostMapping()
//    public ResponseEntity<AuthenticationResponse> doLogin(@RequestBody AuthenticationRequest authenticationRequest){
//        try {
//            AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
//            return ResponseEntity.ok(authenticationResponse);
//        } catch (Exception e) {
//            return ResponseEntity.status(401).body(null);
//        }
//    }






}
