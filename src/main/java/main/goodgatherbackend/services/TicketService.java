package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.TicketDTO;
import main.goodgatherbackend.mappers.TicketMapper;
import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.Ticket;
import main.goodgatherbackend.repositories.EventRepository;
import main.goodgatherbackend.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private TicketRepository ticketRepository;
    private EventRepository eventRepository;
    private TicketMapper ticketMapper;

    public List<TicketDTO> getAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return ticketMapper.toDTOList(tickets);
    }

    public Integer get24hBoughtTickets(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return ticketRepository.sumTicketsBoughtInLast24h(event.getId(), yesterday);
    }

    public List<TicketDTO> getTicketsByEventAndUser(Integer eventId, Integer userId) {
        List<Ticket> tickets = ticketRepository.findByEventIdAndUserId(eventId, userId);
        return ticketMapper.toDTOList(tickets);
    }

    public void saveTicket(TicketDTO ticketDTO) {
        Ticket ticket = ticketMapper.toModel(ticketDTO);
        ticketRepository.save(ticket);
    }
}
