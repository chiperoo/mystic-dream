package dream.mystic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dream.mystic.domain.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
	//Optional<Trip> findById(Long id);

	Collection<Trip> findByCustomerId(Long id);

}
