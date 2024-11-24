package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.TicketDTO;
import main.goodgatherbackend.services.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@AllArgsConstructor
public class TicketController {
    private TicketService ticketService;

    @GetMapping()
    public List<TicketDTO> getAll() {
        return ticketService.getAll();
    }

    @GetMapping("/byEvent/{eventId}/boughtInLast/24h")
    public Integer get24hBoughtTickets(@PathVariable Integer eventId) {
        return ticketService.get24hBoughtTickets(eventId);
    }

    @GetMapping("/byEvent/{eventId}/byUser/{userId}")
    public List<TicketDTO> getTicketsByEventAndUser(@PathVariable Integer eventId, @PathVariable Integer userId) {
        return ticketService.getTicketsByEventAndUser(eventId, userId);
    }

}
