package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.ClientDTO;
import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.mappers.ClientMapper;
import main.goodgatherbackend.mappers.UserClientMapper;
import main.goodgatherbackend.models.Client;
import main.goodgatherbackend.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    private UserClientMapper userClientMapper;

    /**
     * Devuelve todos los clientes
     * @return List<ClientDTO>
     */
    public List<ClientDTO> getAll() {
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toDTOList(clients);
    }

    /**
     * Devuelve un cliente por su id
     * @param idClient
     * @return UserClientDTO
     */
    public UserClientDTO findById(Integer idClient) {
        Client client = clientRepository.findById(idClient).orElse(null);
        return userClientMapper.toDTO(client);
    }
}
