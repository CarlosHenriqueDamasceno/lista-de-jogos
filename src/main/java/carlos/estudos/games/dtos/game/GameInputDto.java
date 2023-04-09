package carlos.estudos.games.dtos.game;

import carlos.estudos.games.models.Game.GameStatus;

public record GameInputDto(String name, GameStatus status, Long developerId) {
}
