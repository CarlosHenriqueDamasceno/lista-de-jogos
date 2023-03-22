package carlos.estudos.games.controllers;

import carlos.estudos.games.dtos.developer.DeveloperOutputDto;
import carlos.estudos.games.models.Developer;
import carlos.estudos.games.repositories.DeveloperRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

    private final DeveloperRepository repository;

    public DeveloperController(DeveloperRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DeveloperOutputDto> getAll(){
        return repository.findAll().stream().map(e -> developerToOutput(e)).toList();
    }

    private DeveloperOutputDto developerToOutput(Developer developer) {
        return new DeveloperOutputDto(developer.getId(), developer.getName());
    }
}
