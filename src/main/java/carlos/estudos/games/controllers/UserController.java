package carlos.estudos.games.controllers;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import carlos.estudos.games.dtos.user.LoginDto;
import carlos.estudos.games.dtos.user.UserInputDto;
import carlos.estudos.games.dtos.user.UserOutputDto;
import carlos.estudos.games.models.User;
import carlos.estudos.games.repositories.UserRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtEncoder jwtEncoder;
	private final UserDetailsService userDetailsService;

	@PostMapping
	public ResponseEntity<UserOutputDto> create(@RequestBody UserInputDto data) {
		User user = new User();
		user = parseInputToUser(user, data);
		repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).body(userToOutput(user));
	}

	@PostMapping("auth")
	public JwtResponse auth(@RequestBody LoginDto data) {
		var user = userDetailsService.loadUserByUsername(data.email());
		return new JwtResponse(generateToken(user));
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

	private String generateToken(UserDetails user) {
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now())
				.subject(user.getUsername()).claim("scope", createScope(user)).build();
		JwtEncoderParameters paramters = JwtEncoderParameters.from(claims);
		return jwtEncoder.encode(paramters).getTokenValue();
	}

	private String createScope(UserDetails user) {
		return user.getAuthorities().stream().map(a -> a.getAuthority())
				.collect(Collectors.joining(" "));
	}
}


record JwtResponse(String token) {
}
