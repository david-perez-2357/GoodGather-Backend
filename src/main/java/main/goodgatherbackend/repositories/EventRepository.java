package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "SELECT * FROM event e WHERE e.deleted = 0 AND e.end_date > CURRENT_TIMESTAMP", nativeQuery = true)
    List<Event> findActiveEvents();
}
