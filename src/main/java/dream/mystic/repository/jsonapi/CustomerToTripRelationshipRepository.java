/**
 *  This class is used for Katharsis so to know the relationship between domain objects
 */
package dream.mystic.repository.jsonapi;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dream.mystic.domain.ActivityLog;
import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.TripRepository;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.RelationshipRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class CustomerToTripRelationshipRepository extends RelationshipRepositoryBase<Customer, Long, Trip, Long> {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private TripRepository tripRepository;
	
	public CustomerToTripRelationshipRepository() {
		super(Customer.class, Trip.class);
	}

	@Override
    public void setRelation(Customer customer, Long tripId, String fieldName) {
        // not for many-to-many
    }

    @Override
    public void setRelations(Customer customer, Iterable<Long> tripIds, String fieldName) {
        final Set<Trip> trips = new HashSet<Trip>();
        trips.addAll(tripRepository.findAll(tripIds));
        customer.setTrips(trips);
        customerRepository.save(customer);
    }

    @Override
    public void addRelations(Customer customer, Iterable<Long> tripIds, String fieldName) {
        final Set<Trip> trips = customer.getTrips();
        trips.addAll(tripRepository.findAll(tripIds));
        customer.setTrips(trips);
        customerRepository.save(customer);
    }

    @Override
    public void removeRelations(Customer customer, Iterable<Long> tripIds, String fieldName) {
        final Set<Trip> trips = customer.getTrips();
        trips.removeAll(tripRepository.findAll(tripIds));
        customer.setTrips(trips);
        customerRepository.save(customer);
    }

    @Override
    public ResourceList<Trip> findManyTargets(Long sourceId, String fieldName, QuerySpec args0) {
        final Customer customer = customerRepository.findOne(sourceId);
        return args0.apply(customer.getTrips());
    }
    
    
//	@Override
//	public Trip findOne(Long customerlId, QuerySpec arg0) {
//		// not for many-to-many
//		return null;
//	}
	
}
