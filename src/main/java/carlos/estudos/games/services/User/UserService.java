package carlos.estudos.games.services.user;

import java.util.List;
import org.springframework.data.domain.Pageable;
import carlos.estudos.games.dtos.user.UserInputDto;
import carlos.estudos.games.dtos.user.UserOutputDto;
import carlos.estudos.games.dtos.user.UserUpdateInputDto;

public interface UserService {
    public UserOutputDto create(UserInputDto data);

    public UserOutputDto update(Long id, UserUpdateInputDto data);

    public List<UserOutputDto> getAll(Pageable pagination);

    public UserOutputDto get(Long id);

    public void delete(Long id);
}
