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
import carlos.estudos.games.dtos.user.UserInputDto;
import carlos.estudos.games.dtos.user.UserOutputDto;
import carlos.estudos.games.dtos.user.UserUpdateInputDto;
import carlos.estudos.games.services.User.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController extends BaseController {

	private final UserService service;

	@GetMapping()
	public List<UserOutputDto> getAll(@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> pageSize) {
		return service.getAll(parsePagination(page, pageSize));
	}

	@GetMapping("/{id}")
	public UserOutputDto get(@PathVariable("id") Long id) {
		return service.get(id);
	}

	@PostMapping
	public ResponseEntity<UserOutputDto> create(@RequestBody UserInputDto data) {
		var result = service.create(data);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.id()).toUri();
		return ResponseEntity.created(location).body(result);
	}

	@PutMapping("/{id}")
	public UserOutputDto update(@PathVariable("id") Long id, @RequestBody UserUpdateInputDto data) {
		return service.update(id, data);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		service.delete(id);
	}
}
