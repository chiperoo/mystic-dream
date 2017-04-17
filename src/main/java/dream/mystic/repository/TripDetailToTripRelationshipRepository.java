/**
 *  This class is used for Katharsis so to know the relationship between domain objects
 */
package dream.mystic.repository;

import org.springframework.stereotype.Component;

import dream.mystic.domain.Trip;
import dream.mystic.domain.TripDetail;
import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class TripDetailToTripRelationshipRepository extends RelationshipRepositoryBase<TripDetail, Long, Trip, Long> {

	public TripDetailToTripRelationshipRepository() {
		super(TripDetail.class, Trip.class);
	}

}
