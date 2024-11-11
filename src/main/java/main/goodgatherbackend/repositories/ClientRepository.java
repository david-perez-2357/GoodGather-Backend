package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
