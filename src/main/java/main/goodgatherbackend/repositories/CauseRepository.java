package main.goodgatherbackend.repositories;

import main.goodgatherbackend.models.Cause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CauseRepository extends JpaRepository<Cause, Integer> {
    @Query("""
        SELECT c
        FROM Cause c
        JOIN User u_creador ON c.owner.id = u_creador.id
        JOIN User u_actual ON u_actual.id = :id_current_user
        JOIN Client cl ON cl.user.id = u_creador.id
        JOIN Client cl2 ON cl2.user.id = u_actual.id
        WHERE 
            c.scope = 2
            OR (c.scope = 1 AND cl.country = cl2.country)
            OR (c.scope = 0 AND cl.province = cl2.province)
        """)
    List<Cause> getCausesInUsersRange(Integer id_current_user);
}
