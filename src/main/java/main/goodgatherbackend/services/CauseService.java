package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.CauseDTO;
import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.mappers.CauseMapper;
import main.goodgatherbackend.mappers.EventMapper;
import main.goodgatherbackend.models.Cause;
import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.Ticket;
import main.goodgatherbackend.repositories.CauseRepository;
import main.goodgatherbackend.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CauseService {
    private final EventRepository eventRepository;
    private CauseRepository causeRepository;
    private CauseMapper causeMapper;
    private EventMapper eventMapper;

    public List<CauseDTO> getAll() {
        List<Cause> causes = causeRepository.findAll();
        return causeMapper.toDTOList(causes);
    }

    public CauseDTO getById(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow();
        return causeMapper.toDTO(cause);
    }

    public Double getCauseFunds(Integer id) {
        System.out.println("Getting funds for cause with id: " + id);
        Cause cause = causeRepository.findById(id).orElseThrow();
        List<Ticket> tickets = cause.getEvents().stream().map(Event::getTickets).flatMap(List::stream).toList();
        System.out.println("Tickets: " + tickets.size());
        return tickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

    // Quiero un metodo que coja todos los eventos de una causa utilizando el metodo del causeRepository
    public List<EventDTO> getEventsFromCause(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow();
        List<Event> events = eventRepository.findAllByCause(cause);
        return eventMapper.toDTOList(events);
    }

}
