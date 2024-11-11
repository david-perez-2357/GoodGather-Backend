package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
