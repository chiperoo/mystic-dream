/**
 *  This class is used for Katharsis so that it can publish API operations.
 */
package dream.mystic.repository.jsonapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.Trip;
import dream.mystic.repository.TripRepository;
import io.katharsis.errorhandling.exception.ResourceNotFoundException;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class TripResourceRepositoryImpl extends ResourceRepositoryBase<Trip,Long> {

	@Autowired
	private TripRepository tripRepository;
	
	public TripResourceRepositoryImpl() {
		super(Trip.class);
	}

	@Override
    public synchronized <S extends Trip> S save(S trip) {
            tripRepository.save(trip);
            return trip;
	}
	
	@Override
	public synchronized Trip findOne(Long tripId, QuerySpec arg0) {
		Trip trip =  tripRepository.findOne(tripId);
		if(trip == null) {
			throw new ResourceNotFoundException("Trip record not found");
		}
		return trip;
	}
	
	@Override
	public synchronized ResourceList<Trip> findAll(QuerySpec arg0) {
		return arg0.apply(tripRepository.findAll());
	}

}
