package dream.mystic.repository;

import org.springframework.stereotype.Component;

import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class CustomerToTripRelationshipRepository extends RelationshipRepositoryBase<Customer, Long, Trip, Long> {

	public CustomerToTripRelationshipRepository() {
		super(Customer.class, Trip.class);
	}
}
