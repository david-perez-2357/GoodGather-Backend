package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.TicketDTO;
import main.goodgatherbackend.services.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<TicketDTO> get24hBoughtTickets(@PathVariable Integer eventId) {
        return ticketService.get24hBoughtTickets(eventId);
    }
}
