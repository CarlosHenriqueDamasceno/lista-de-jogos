package carlos.estudos.games.repositories;

import carlos.estudos.games.models.Game;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {
    public Optional<Game> findById(Long id);
    public Game save(Game game);
    public void delete(Game game);
}
