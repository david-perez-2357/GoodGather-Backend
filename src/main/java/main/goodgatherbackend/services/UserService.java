package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.Ticket;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.repositories.EventRepository;
import main.goodgatherbackend.repositories.TicketRepository;
import main.goodgatherbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private TicketRepository ticketRepository;
    private EventRepository eventRepository;
    private EventService eventService;

    /**
     * Returns the total amount of contributions made by a user
     * @param id Integer
     * @return Double
     */
    public Double getUserContributions(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        return ticketRepository.findAllByUser(user).stream().mapToDouble(Ticket::getPrice).sum();
    }

    /**
     * Returns the total number of events a user has assisted
     * @param id Integer
     * @return Integer
     */
    public Integer getUserNumAssistedEvents(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        Set<Event> events = ticketRepository.findAllByUser(user)
                .stream().map(Ticket::getEvent)
                .filter(eventService::isEventPast)
                .collect(Collectors.toSet());

        return events.size();
    }

    /**
     * Returns the total number of events a user has upcoming
     * @param id Integer
     * @return Integer
     */
    public Integer getUserNumUpcomingEvents(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        Set<Event> events = ticketRepository.findAllByUser(user)
                .stream().map(Ticket::getEvent)
                .filter(event -> !eventService.isEventPast(event))
                .collect(Collectors.toSet());

        return events.size();
    }

    /**
     * Returns the total number of events a user has created
     * @param id Integer
     * @return Integer
     */
    public Integer getUserNumCreatedEvents(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        return eventRepository.findAllByOwner(user).size();
    }
}
