package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Client;
import main.goodgatherbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    /**@
     * findByEmail: busca un cliente por su email.
     * existsClientsByEmail: verifica si ya existe un cliente con el email proporcionado.
     * findByUser: busca un cliente asociado a un usuario específico.
     * @param email
     * @return
     */
    Optional<Client> findByEmail(String email);
    boolean existsClientsByEmail(String email);

    Optional<Client> findByUser(User user);
}
