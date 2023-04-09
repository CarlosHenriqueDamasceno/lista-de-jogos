package carlos.estudos.games.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import carlos.estudos.games.dtos.game.GameInputDto;
import carlos.estudos.games.dtos.game.GameOutputDto;
import carlos.estudos.games.services.game.GameService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor
public class GameController extends BaseController {

    private final GameService service;

    @GetMapping
    public List<GameOutputDto> getGames(@RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize) {
        return service.getAll(parsePagination(page, pageSize));
    }

    @GetMapping("/{id}")
    public GameOutputDto getGame(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping()
    public ResponseEntity<GameOutputDto> createGame(@RequestBody GameInputDto data) {
        var game = service.create(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(game.id()).toUri();
        return ResponseEntity.created(location).body(game);
    }

    @PutMapping("/{id}")
    public GameOutputDto updateGame(@PathVariable Long id, @RequestBody GameInputDto data) {
        var game = service.update(id, data);
        return game;
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        service.delete(id);
    }
}
