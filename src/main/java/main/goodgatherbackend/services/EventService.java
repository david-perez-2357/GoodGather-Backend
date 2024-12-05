package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.mappers.EventMapper;
import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;
    private EventMapper eventMapper;

    public List<EventDTO> getAll() {
        List<Event> events = eventRepository.findByDeletedAndEndDateAfter(0, LocalDateTime.now());
        return eventMapper.toDTOList(events);
    }

    public List<EventDTO> getAllWithoutFilter() {
        List<Event> events = eventRepository.findAll();
        return eventMapper.toDTOList(events);
    }

    public EventDTO getById(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow();
        return eventMapper.toDTO(event);
    }

    public EventDTO create(EventDTO eventDTO) {
        Event event = eventMapper.toModel(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDTO(event);
    }
}
