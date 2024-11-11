package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
