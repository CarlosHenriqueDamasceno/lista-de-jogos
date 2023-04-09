package carlos.estudos.games.services.game;

import java.util.List;
import org.springframework.data.domain.Pageable;
import carlos.estudos.games.dtos.game.GameInputDto;
import carlos.estudos.games.dtos.game.GameOutputDto;

public interface GameService {
    public GameOutputDto create(GameInputDto data);

    public GameOutputDto update(Long id, GameInputDto data);

    public List<GameOutputDto> getAll(Pageable pagination);

    public GameOutputDto get(Long id);

    public void delete(Long id);
}
