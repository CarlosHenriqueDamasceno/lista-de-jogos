package carlos.estudos.games.services.User;

import carlos.estudos.games.dtos.user.LoginDto;
import carlos.estudos.games.dtos.user.LoginOutputDto;

public interface LoginService {
    public LoginOutputDto auth(LoginDto data);
}
