package carlos.estudos.games.services.user;

import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import carlos.estudos.games.dtos.user.LoginDto;
import carlos.estudos.games.dtos.user.LoginOutputDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserDetailsService userDetailsService;
    private final JwtEncoder jwtEncoder;

    @Override
    public LoginOutputDto auth(LoginDto data) {
        var user = userDetailsService.loadUserByUsername(data.email());
        return new LoginOutputDto(generateToken(user));
    }

    private String generateToken(UserDetails user) {
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30)).subject(user.getUsername())
                .claim("scope", createScope(user)).build();
        JwtEncoderParameters paramters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(paramters).getTokenValue();
    }

    private String createScope(UserDetails user) {
        return user.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }
}
