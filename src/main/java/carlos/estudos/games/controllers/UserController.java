package carlos.estudos.games.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import carlos.estudos.games.configs.JwtProvider;
import carlos.estudos.games.dtos.user.LoginDto;
import carlos.estudos.games.dtos.user.UserInputDto;
import carlos.estudos.games.dtos.user.UserOutputDto;
import carlos.estudos.games.models.User;
import carlos.estudos.games.repositories.UserRepository;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtProvider jwtProvider;

	public UserController(UserRepository repository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
			AuthenticationConfiguration authenticationConfiguration) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtProvider = jwtProvider;
	}

	@PostMapping
	public ResponseEntity<UserOutputDto> create(@RequestBody UserInputDto data) {
		User user = new User();
		user = parseInputToUser(user, data);
		repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(location).body(userToOutput(user));
	}

	@PostMapping("auth")
	public String auth(@RequestBody LoginDto data) throws Exception {
		AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(data.email(), data.password()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return jwtProvider.generateToken(auth);
	}

	private User parseInputToUser(User user, UserInputDto data) {
		user.setName(data.name());
		user.setEmail(data.email());
		user.setPassword(passwordEncoder.encode(data.password()));
		return user;
	}

	private UserOutputDto userToOutput(User user) {
		return new UserOutputDto(user.getId(), user.getName(), user.getEmail());
	}
}
