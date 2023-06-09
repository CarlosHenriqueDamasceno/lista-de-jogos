package carlos.estudos.games.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.estudos.games.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByEmail(String email);
}
