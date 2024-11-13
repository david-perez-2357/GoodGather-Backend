package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.services.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {
    private EventService eventService;

    @GetMapping()
    public List<EventDTO> getAll() {
        return eventService.getAll();
    }
}
