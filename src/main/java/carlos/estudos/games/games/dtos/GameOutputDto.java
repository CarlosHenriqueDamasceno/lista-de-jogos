package carlos.estudos.games.games.dtos;

import carlos.estudos.games.developers.models.Developer;
import carlos.estudos.games.games.models.GameStatus;

public record GameOutputDto(Long id, String name, GameStatus status, Developer developer) {
}
