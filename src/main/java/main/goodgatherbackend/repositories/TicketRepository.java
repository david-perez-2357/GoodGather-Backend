package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByEventAndPurchaseDateIsAfter(Event event, LocalDateTime purchaseDate);
}
