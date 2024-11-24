package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT SUM(t.amount) FROM Ticket t WHERE t.event.id = :eventId AND t.purchaseDate >= :purchaseDate")
    Integer sumTicketsBoughtInLast24h(Integer eventId, LocalDateTime purchaseDate);

    @Query("SELECT SUM(t.amount) FROM Ticket t WHERE t.event.id = :eventId")
    Integer sumAmountByEventId(Integer eventId);

    List<Ticket> findByEventIdAndUserId(Integer eventId, Integer userId);
}
