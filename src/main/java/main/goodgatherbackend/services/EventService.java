package main.goodgatherbackend.services;

import jdk.jfr.Timestamp;
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

    public EventDTO getById(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow();
        return eventMapper.toDTO(event);
    }
}