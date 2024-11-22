package main.goodgatherbackend.mappers;

import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.models.Cause;
import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.repositories.CauseRepository;
import main.goodgatherbackend.repositories.TicketRepository;
import main.goodgatherbackend.repositories.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public abstract class EventMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CauseRepository causeRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "boughtTickets", source = "id", qualifiedByName = "boughtTickets")
    @Mapping(target = "startDate", source = "startDate", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "idOwner", source = "owner.id")
    @Mapping(target = "idCause", source = "cause.id")
    public abstract EventDTO toDTO(Event event);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "startDate", source = "startDate", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "owner", source = "idOwner", qualifiedByName = "idOwner")
    @Mapping(target = "cause", source = "idCause", qualifiedByName = "idCause")
    public abstract Event toModel(EventDTO eventDTO);

    public abstract List<EventDTO> toDTOList(List<Event> events);
    public abstract List<Event> toModelList(List<EventDTO> eventDTOs);

    @Named("boughtTickets")
    public Integer boughtTickets(Integer eventId) {
        return ticketRepository.sumAmountByEventId(eventId);
    }

    @Named("idOwner")
    public User mapToOwner(Integer ownerId) {
        return userRepository.findById(ownerId).orElse(null);
    }

    @Named("idCause")
    public Cause mapToCause(Integer causeId) {
        return causeRepository.findById(causeId).orElse(null);
    }
}
