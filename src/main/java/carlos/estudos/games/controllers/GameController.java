package carlos.estudos.games.controllers;

import carlos.estudos.games.Exceptions.RecordNotFoundException;
import carlos.estudos.games.dtos.game.GameInputDto;
import carlos.estudos.games.dtos.game.GameOutputDto;
import carlos.estudos.games.models.Developer;
import carlos.estudos.games.models.Game;
import carlos.estudos.games.repositories.DeveloperRepository;
import carlos.estudos.games.repositories.GameRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/games")
public class GameController extends BaseController{

    private final GameRepository repository;
    private final DeveloperRepository developerRepository;

    public GameController(GameRepository repository, DeveloperRepository developerRepository) {
        this.repository = repository;
        this.developerRepository = developerRepository;
    }

    @GetMapping
    public List<GameOutputDto> getGames(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize) {
        return repository.findAll(parsePagination(page, pageSize)).stream().map(e -> gameToOutput(e)).toList();
    }

    @GetMapping("/{id}")
    public GameOutputDto getGame(@PathVariable Long id) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Jogo não encontrado!"));
        return gameToOutput(game);
    }

    @PostMapping()
    public GameOutputDto createGame(@RequestBody GameInputDto data) {
        Game game = new Game();
        game = parseInputToGame(game, data);
        repository.save(game);
        return gameToOutput(game);
    }

    @PutMapping("/{id}")
    public GameOutputDto updateGame(@PathVariable Long id, @RequestBody GameInputDto data) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Jogo não encontrado!"));
        game = parseInputToGame(game, data);
        return gameToOutput(game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Jogo não encontrado!"));
        repository.delete(game);
    }

    private Game parseInputToGame(Game game, GameInputDto data) {
        Developer developer = developerRepository.findById(data.developerId())
                .orElseThrow(() -> new RecordNotFoundException("Desenvolvedora não encontrada!"));
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