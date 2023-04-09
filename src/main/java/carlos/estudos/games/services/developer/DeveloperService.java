package carlos.estudos.games.services.developer;

import java.util.List;
import carlos.estudos.games.dtos.developer.DeveloperOutputDto;

public interface DeveloperService {
    public List<DeveloperOutputDto> getAll();
}
