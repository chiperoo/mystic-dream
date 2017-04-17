/**
 *  This class is used for Katharsis so that it can publish API operations
 */
package dream.mystic.repository;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.Trip;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class TripResourceRepository extends ResourceRepositoryBase<Trip,Long> {
	
	private static final AtomicLong ID_GENERATOR = new AtomicLong();

	@Autowired
	private TripRepository tripRepository;
	
	public TripResourceRepository() {
		super(Trip.class);
	}

	@Override
    public synchronized <S extends Trip> S save(S trip) {
			if(trip.getId() == null) {
				long count = tripRepository.count();
				if(count > ID_GENERATOR.get()) {
					ID_GENERATOR.set(tripRepository.count() + 1);
				}
				trip.setId(ID_GENERATOR.getAndIncrement());
			}
            tripRepository.save(trip);
            return trip;
	}
	
	@Override
	public Trip findOne(Long tripId, QuerySpec arg0) {
		return tripRepository.findOne(tripId);
	}
	
	@Override
	public ResourceList<Trip> findAll(QuerySpec arg0) {
		return arg0.apply(tripRepository.findAll());
	}
}
