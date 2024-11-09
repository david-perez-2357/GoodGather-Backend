package repositories;

import models.Cause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CauseRepository extends JpaRepository<Cause, Integer> {
}
