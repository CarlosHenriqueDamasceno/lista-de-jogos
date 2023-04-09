package carlos.estudos.games.services.user;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import carlos.estudos.games.Exceptions.RecordNotFoundException;
import carlos.estudos.games.dtos.user.UserInputDto;
import carlos.estudos.games.dtos.user.UserOutputDto;
import carlos.estudos.games.dtos.user.UserUpdateInputDto;
import carlos.estudos.games.models.User;
import carlos.estudos.games.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserOutputDto> getAll(Pageable pagination) {
        return repository.findAll(pagination).stream().map(e -> userToOutput(e)).toList();
    }

    @Override
    public UserOutputDto get(Long id) {
        var user = repository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Usuário não encontrado com o id: " + id));
        return userToOutput(user);
    }

    @Override
    public UserOutputDto create(UserInputDto data) {
        User user = new User();
        user = parseInputToUser(user, data);
        repository.save(user);
        return userToOutput(user);
    }

    @Override
    public UserOutputDto update(Long id, UserUpdateInputDto data) {
        var user = repository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Usuário não encontrado com o id: " + id));
        BeanUtils.copyProperties(data, user);
        repository.save(user);
        return userToOutput(user);
    }

    @Override
    public void delete(Long id) {
        var user = repository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Usuário não encontrado com o id: " + id));
        repository.delete(user);
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
