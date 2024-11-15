package main.goodgatherbackend.mappers;

import main.goodgatherbackend.dtos.TicketDTO;
import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.Ticket;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.repositories.EventRepository;
import main.goodgatherbackend.repositories.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;

@Mapper
public abstract class TicketMapper {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idEvent", source = "event.id")
    @Mapping(target = "idUser", source = "user.id")
    @Mapping(target = "purchaseDate", source = "purchaseDate", dateFormat = "yyyy-MM-dd HH:mm")
    public abstract TicketDTO toDTO(Ticket ticket);

    @Mapping(target = "event", source = "idEvent", qualifiedByName = "idEvent")
    @Mapping(target = "user", source = "idUser", qualifiedByName = "idUser")
    @Mapping(target = "purchaseDate", source = "purchaseDate", dateFormat = "yyyy-MM-dd HH:mm")
    public abstract Ticket toModel(TicketDTO ticketDTO);

    public abstract List<TicketDTO> toDTOList(List<Ticket> tickets);
    public abstract List<Ticket> toModelList(List<TicketDTO> ticketDTOs);

    @Named("idEvent")
    public Event mapToEvent(Integer eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    @Named("idUser")
    public User mapToUser(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
