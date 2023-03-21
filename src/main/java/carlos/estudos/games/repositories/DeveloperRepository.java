package carlos.estudos.games.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import carlos.estudos.games.models.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
