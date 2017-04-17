package dream.mystic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dream.mystic.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);
}
