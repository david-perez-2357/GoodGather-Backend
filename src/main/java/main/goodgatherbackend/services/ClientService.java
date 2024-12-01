package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.ClientDTO;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.mappers.ClientMapper;
import main.goodgatherbackend.mappers.UserClientMapper;
import main.goodgatherbackend.models.Client;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.repositories.ClientRepository;
import main.goodgatherbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    private UserClientMapper userClientMapper;
    private UserRepository userRepository;


    public List<ClientDTO> getAll() {
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toDTOList(clients);
    }

    public UserClientDTO findById(Integer idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        return userClientMapper.toDTO(client);
    }

    public void checkUserExist(UserClientDTO userClientDTO){
        if (userRepository.findByUsername(userClientDTO.getUsername()).isPresent()) {
            throw new IllegalStateException("User name is already in use");
        }
    }


    public void saveClient(UserClientDTO userClientDTO) {
        User user = userRepository.findByUsername(userClientDTO.getUsername()).orElseThrow();
        Client client = userClientMapper.toClient(userClientDTO);
        client.setUser(user);

        clientRepository.save(client);
    }
}
