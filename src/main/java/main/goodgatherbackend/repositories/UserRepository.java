package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**@
     * findByUsername: busca un usuario por su username.
     * existsByUsername: verifica si un username ya está registrado.
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

}
