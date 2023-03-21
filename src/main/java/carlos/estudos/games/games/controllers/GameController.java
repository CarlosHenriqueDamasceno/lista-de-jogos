package carlos.estudos.games.games.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import carlos.estudos.games.Exceptions.RecordNotFoundException;
import carlos.estudos.games.developers.models.Developer;
import carlos.estudos.games.developers.repositories.DeveloperRepository;
import carlos.estudos.games.games.dtos.GameInputDto;
import carlos.estudos.games.games.dtos.GameOutputDto;
import carlos.estudos.games.games.models.Game;
import carlos.estudos.games.games.repositories.GameRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameRepository repository;
    private final DeveloperRepository developerRepository;

    public GameController(GameRepository repository, DeveloperRepository developerRepository) {
        this.repository = repository;
        this.developerRepository = developerRepository;
    }

    @GetMapping
    public List<GameOutputDto> getGames() {
        return repository.findAll().stream().map(e -> gameToOutput(e)).toList();
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
