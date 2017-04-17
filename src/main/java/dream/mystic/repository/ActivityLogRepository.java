package dream.mystic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dream.mystic.domain.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
	Optional<ActivityLog> findById(Long id);

	//Collection<ActivityLog> findByTripId(Long id);
	
	Collection<ActivityLog> findByCustomerId(Long id);
	
	Collection<ActivityLog> findByUserId(Long id);
}
