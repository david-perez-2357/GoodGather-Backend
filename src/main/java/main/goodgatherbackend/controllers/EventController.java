package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {
    private EventService eventService;

    @GetMapping()
    public ResponseEntity<List<EventDTO>> getAll() {
        try {
            return ResponseEntity.ok(eventService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(eventService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<EventDTO> create(@RequestBody EventDTO eventDTO) {
        try{
            return ResponseEntity.ok(eventService.create(eventDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
