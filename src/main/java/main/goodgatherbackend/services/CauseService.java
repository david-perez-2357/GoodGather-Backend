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

    /**
     * Devuelve todas las causas
     * @return List<CauseDTO>
     */
    public List<CauseDTO> getAll() {
        List<Cause> causes = causeRepository.findAll();
        return causeMapper.toDTOList(causes);
    }

    /**
     * Devuelve una causa por su id
     * @param id
     * @return CauseDTO
     */
    public CauseDTO getById(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow();
        return causeMapper.toDTO(cause);
    }

    /**
     * Devuelve el total de fondos recaudados por una causa
     * @param id
     * @return Double
     */
    public Double getCauseFunds(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow();
        List<Ticket> tickets = cause.getEvents().stream().map(Event::getTickets).flatMap(List::stream).toList();
        return tickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

    /**
     * Devuelve todos los eventos de una causa
     * @param id
     * @return List<EventDTO>
     */
    public List<EventDTO> getEventsFromCause(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow();
        List<Event> events = eventRepository.findAllByCause(cause);
        return eventMapper.toDTOList(events);
    }

    /**
     * Devuelve todas las causas en el rango de un usuario
     * @param userId
     * @return List<CauseDTO>
     */
    public List<CauseDTO> getCausesInUsersRange(Integer userId) {
        List<Cause> causes = causeRepository.getCausesInUsersRange(userId);
        return causeMapper.toDTOList(causes);
    }

    /**
     * Crea una causa
     * @param causeDTO
     * @return CauseDTO
     */
    public CauseDTO createCause(CauseDTO causeDTO) {
        Cause cause = causeMapper.toModel(causeDTO);
        cause = causeRepository.save(cause);
        return causeMapper.toDTO(cause);
    }
}
