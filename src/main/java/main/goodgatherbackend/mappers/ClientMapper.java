package main.goodgatherbackend.mappers;

import main.goodgatherbackend.dtos.ClientDTO;
import main.goodgatherbackend.models.Client;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.repositories.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public abstract class ClientMapper {
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idUser", source = "user.id")
    public abstract ClientDTO toDTO(Client client);

    @Mapping(target = "user", source = "idUser", qualifiedByName = "idUser")
    public abstract Client toModel(ClientDTO clientDTO);

    public abstract List<ClientDTO> toDTOList(List<Client> clients);
    public abstract List<Client> toModelList(List<ClientDTO> clientDTOs);

    @Named("idUser")
    public User mapToUser(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
