package carlos.estudos.games.games.dtos;

import carlos.estudos.games.games.models.GameStatus;

public record GameInputDto(String name, GameStatus status, Long developerId) {
}
