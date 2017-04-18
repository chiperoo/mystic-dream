package dream.mystic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import dream.mystic.domain.Trip;

@Transactional(readOnly = true)
public interface TripRepository extends JpaRepository<Trip, Long> {
	Optional<Trip> findById(Long id);

	Collection<Trip> findByCustomers(Long id);

}
