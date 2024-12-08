package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Client;
import main.goodgatherbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
    boolean existsClientsByEmail(String email);

    Optional<Client> findByUser(User user);
}
