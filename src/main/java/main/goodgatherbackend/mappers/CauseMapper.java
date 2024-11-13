package main.goodgatherbackend.mappers;

import main.goodgatherbackend.dtos.CauseDTO;
import main.goodgatherbackend.enumerated.Scope;
import main.goodgatherbackend.models.Cause;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.repositories.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public abstract class CauseMapper {
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "scope", source = "scope", qualifiedByName = "scopeToString")
    @Mapping(target = "idOwner", source = "owner.id")
    public abstract CauseDTO toDTO(Cause cause);

    @Mapping(target = "scope", source = "scope", qualifiedByName = "stringToScope")
    @Mapping(target = "owner", source = "idOwner", qualifiedByName = "idOwner")
    public abstract Cause toModel(CauseDTO causeDTO);

    public abstract List<CauseDTO> toDTOList(List<Cause> causes);
    public abstract List<Cause> toModelList(List<CauseDTO> causeDTOs);

    @Named("scopeToString")
    public String mapToScope(Scope scope) {
        return scope.name();
    }

    @Named("stringToScope")
    public Scope mapToScope(String scope) {
        return Scope.valueOf(scope);
    }

    @Named("idOwner")
    public User mapToOwner(Integer ownerId) {
        return userRepository.findById(ownerId).orElse(null);
    }
}
