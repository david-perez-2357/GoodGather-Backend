package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.TicketDTO;
import main.goodgatherbackend.mappers.TicketMapper;
import main.goodgatherbackend.models.Ticket;
import main.goodgatherbackend.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private TicketRepository ticketRepository;
    private TicketMapper ticketMapper;

    public List<TicketDTO> getAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return ticketMapper.toDTOList(tickets);
    }
}
