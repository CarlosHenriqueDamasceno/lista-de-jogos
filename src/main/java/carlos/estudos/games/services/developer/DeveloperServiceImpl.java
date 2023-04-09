package carlos.estudos.games.services.developer;

import java.util.List;
import org.springframework.stereotype.Service;
import carlos.estudos.games.dtos.developer.DeveloperOutputDto;
import carlos.estudos.games.models.Developer;
import carlos.estudos.games.repositories.DeveloperRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository repository;

    @Override
    public List<DeveloperOutputDto> getAll() {
        return repository.findAll().stream().map(e -> developerToOutput(e)).toList();
    }

    private DeveloperOutputDto developerToOutput(Developer developer) {
        return new DeveloperOutputDto(developer.getId(), developer.getName());
    }

}
