package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.ClientDTO;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.services.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
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
