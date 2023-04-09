package carlos.estudos.games.services.game;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import carlos.estudos.games.Exceptions.RecordNotFoundException;
import carlos.estudos.games.dtos.game.GameInputDto;
import carlos.estudos.games.dtos.game.GameOutputDto;
import carlos.estudos.games.models.Game;
import carlos.estudos.games.repositories.DeveloperRepository;
import carlos.estudos.games.repositories.GameRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository repository;
    private final DeveloperRepository developerRepository;

    @Override
    public List<GameOutputDto> getAll(Pageable pagination) {
        return repository.findAll(pagination).stream().map(e -> gameToOutput(e)).toList();
    }

    @Override
    public GameOutputDto get(Long id) {
        var game = repository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Usuário não encontrado com o id: " + id));
        return gameToOutput(game);
    }

    @Override
    public GameOutputDto create(GameInputDto data) {
        Game game = new Game();
        game = parseInputToGame(game, data);
        repository.save(game);
        return gameToOutput(game);
    }

    @Override
    public GameOutputDto update(Long id, GameInputDto data) {
        var game = repository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Usuário não encontrado com o id: " + id));
        BeanUtils.copyProperties(data, game);
        repository.save(game);
        return gameToOutput(game);
    }

    @Override
    public void delete(Long id) {
        var game = repository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Usuário não encontrado com o id: " + id));
        repository.delete(game);
    }

    private Game parseInputToGame(Game game, GameInputDto data) {
        var developer = developerRepository.findById(data.developerId())
                .orElseThrow(() -> new RecordNotFoundException("Desenvolvedor não encontrado"));
        game.setName(data.name());
        game.setStatus(data.status());
        game.setDeveloper(developer);
        return game;
    }

    private GameOutputDto gameToOutput(Game game) {
        return new GameOutputDto(game.getId(), game.getName(), game.getStatus(),
                game.getDeveloper());
    }

}
