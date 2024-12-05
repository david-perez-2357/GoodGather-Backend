package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Cause;
import main.goodgatherbackend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByDeletedAndEndDateAfter(int deleted, LocalDateTime now);

    List<Event> findAllByCause(Cause cause);
}
