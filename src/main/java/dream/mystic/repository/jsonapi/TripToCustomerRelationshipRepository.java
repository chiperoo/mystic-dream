/**
 *  This class is used for Katharsis so to know the relationship between domain objects
 */
package dream.mystic.repository.jsonapi;

import org.springframework.stereotype.Component;

import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class TripToCustomerRelationshipRepository extends RelationshipRepositoryBase<Trip, Long, Customer, Long> {

	public TripToCustomerRelationshipRepository() {
		super(Trip.class, Customer.class);
	}
}
