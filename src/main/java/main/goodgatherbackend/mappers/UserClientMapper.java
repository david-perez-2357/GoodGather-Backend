package main.goodgatherbackend.mappers;


import main.goodgatherbackend.dtos.UserClientDTO;
import main.goodgatherbackend.models.Client;
import main.goodgatherbackend.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class UserClientMapper {
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "idClient", source = "id")
    public abstract UserClientDTO toDTO(Client client);

    /**
     * Convierte un objeto UserClientDTO a una entidad Client.
     * Mapea explícitamente idClient del DTO al campo id de la entidad.
     * Ignora propiedades no mapeadas, como username y password.
     * @param userClientDTO
     * @return
     */
    @Mapping(target = "id", source = "idClient")
    @BeanMapping(ignoreUnmappedSourceProperties = {"id", "username", "password"})
    public abstract Client toClient(UserClientDTO userClientDTO);

    @Mapping(target = "id", source = "id")
    @BeanMapping(ignoreUnmappedSourceProperties = {"idClient", "firstname", "surname", "email", "birthdate", "province", "country"})
    public abstract User toUser(UserClientDTO userClientDTO);

    public abstract List<UserClientDTO> toDTOList(List<Client> clients);
    public abstract List<Client> toClients(List<UserClientDTO> userClientDTOs);
    public abstract List<User> toUsers(List<UserClientDTO> userClientDTOs);
}
