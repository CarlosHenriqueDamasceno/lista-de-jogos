package carlos.estudos.games.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.estudos.games.models.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
