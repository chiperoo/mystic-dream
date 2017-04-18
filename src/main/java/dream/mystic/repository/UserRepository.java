package dream.mystic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import dream.mystic.domain.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);
}
