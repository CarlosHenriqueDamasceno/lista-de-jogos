package carlos.estudos.games.developers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import carlos.estudos.games.developers.models.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
