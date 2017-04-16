package dream.mystic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dream.mystic.domain.TripDetail;

public interface TripDetailRepository extends JpaRepository<TripDetail, Long> {
	//Optional<TripDetail> findById(Long id);

	Collection<TripDetail> findByTripId(Long id);
}
