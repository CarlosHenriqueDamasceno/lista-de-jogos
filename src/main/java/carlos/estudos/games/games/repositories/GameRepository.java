package carlos.estudos.games.games.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import carlos.estudos.games.games.models.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
