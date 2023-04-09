package carlos.estudos.games.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import carlos.estudos.games.dtos.developer.DeveloperOutputDto;
import carlos.estudos.games.services.developer.DeveloperService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/developers")
@AllArgsConstructor
public class DeveloperController {

    private final DeveloperService service;

    @GetMapping
    public List<DeveloperOutputDto> getAll() {
        return service.getAll();
    }
}
