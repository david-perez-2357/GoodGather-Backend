package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.models.Ticket;
import main.goodgatherbackend.models.User;
import main.goodgatherbackend.repositories.TicketRepository;
import main.goodgatherbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private TicketRepository ticketRepository;

    /**
     * Returns the total amount of contributions made by a user
     * @param id Integer
     * @return Double
     */
    public Double getUserContributions(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        return ticketRepository.findAllByUser(user).stream().mapToDouble(Ticket::getPrice).sum();
    }
}
