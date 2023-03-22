package carlos.estudos.games.dtos.game;

import carlos.estudos.games.models.Developer;
import carlos.estudos.games.models.GameStatus;

public record GameOutputDto(Long id, String name, GameStatus status, Developer developer) {
}
