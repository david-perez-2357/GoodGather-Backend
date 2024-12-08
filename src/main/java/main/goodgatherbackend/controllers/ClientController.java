package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.ClientDTO;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.repositories.ClientRepository;
import main.goodgatherbackend.repositories.UserRepository;
import main.goodgatherbackend.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;
    private ClientService clientService;

    @GetMapping()
    public List<ClientDTO> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public UserClientDTO getById(@PathVariable Integer id) {
        return clientService.findById(id);
    }


}
