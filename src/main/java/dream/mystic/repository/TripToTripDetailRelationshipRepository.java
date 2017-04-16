/**
 *  This class is used for Katharsis to determine class relationships
 */
package dream.mystic.repository;

import org.springframework.stereotype.Component;

import dream.mystic.domain.Trip;
import dream.mystic.domain.TripDetail;
import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class TripToTripDetailRelationshipRepository extends RelationshipRepositoryBase<Trip, Long, TripDetail, Long> {

	public TripToTripDetailRelationshipRepository() {
		super(Trip.class, TripDetail.class);
	}
}
