package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.dtos.TicketDTO;
import main.goodgatherbackend.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
public class TicketController {
    private TicketService ticketService;

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByUser(@PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byEvent/{eventId}/boughtInLast/24h")
    public ResponseEntity<Integer> get24hBoughtTickets(@PathVariable Integer eventId) {
        try {
            return ResponseEntity.ok(ticketService.get24hBoughtTickets(eventId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byEvent/{eventId}/byUser/{userId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByEventAndUser(@PathVariable Integer eventId, @PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(ticketService.getTicketsByEventAndUser(eventId, userId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public void create(@RequestBody TicketDTO ticketDTO) {
        try {
            ticketService.saveTicket(ticketDTO);
        } catch (Exception e) {
            ResponseEntity.badRequest().build();
        }
    }
}
