/**
 *  This class is used for Katharsis so that it can publish API operations
 *  
 *  Relationships need to be explicitly defined.
 *  Many to Many relationships are even more verbose
 *  
 */
package dream.mystic.repository.jsonapi;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.TripRepository;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.RelationshipRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class TripToCustomerRelationshipRepository extends RelationshipRepositoryBase<Trip, Long, Customer, Long> {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private TripRepository tripRepository;
	
	public TripToCustomerRelationshipRepository() {
		super(Trip.class, Customer.class);
	}
	
	@Override
    public void setRelation(Trip trip, Long customerId, String fieldName) {
        // not for many-to-many
    }

    @Override
    public void setRelations(Trip trip, Iterable<Long> customerIds, String fieldName) {
        final Set<Customer> customers = new HashSet<Customer>();
        customers.addAll(customerRepository.findAll(customerIds));
        trip.setCustomers(customers);
        tripRepository.save(trip);
    }

    @Override
    public void addRelations(Trip trip, Iterable<Long> customerIds, String fieldName) {
        final Set<Customer> customers = trip.getCustomers();
        customers.addAll(customerRepository.findAll(customerIds));
        trip.setCustomers(customers);
        tripRepository.save(trip);
    }

    @Override
    public void removeRelations(Trip trip, Iterable<Long> customerIds, String fieldName) {
        final Set<Customer> customers = trip.getCustomers();
        customers.removeAll(customerRepository.findAll(customerIds));
        trip.setCustomers(customers);
        tripRepository.save(trip);
    }
    
    @Override
    public ResourceList<Customer> findManyTargets(Long sourceId, String fieldName, QuerySpec args0) {
        final Trip trip = tripRepository.findOne(sourceId);
        return args0.apply(trip.getCustomers());
    }
}
