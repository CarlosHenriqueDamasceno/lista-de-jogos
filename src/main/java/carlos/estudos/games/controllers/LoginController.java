package carlos.estudos.games.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import carlos.estudos.games.dtos.user.LoginDto;
import carlos.estudos.games.dtos.user.LoginOutputDto;
import carlos.estudos.games.services.user.LoginService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/users/auth")
@AllArgsConstructor
public class LoginController {


    private final LoginService loginService;

    @PostMapping()
    public LoginOutputDto auth(@RequestBody LoginDto data) {
        return loginService.auth(data);
    }
}
