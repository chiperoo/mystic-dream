package dream.mystic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.ActivityLogRepository;
import dream.mystic.repository.TripRepository;
import dream.mystic.repository.UserRepository;

@RestController
@RequestMapping("/mystic")
public class MysticController {

	private final CustomerRepository customerRepository;
	private final UserRepository userRepository;
	private final TripRepository tripRepository;
	private final ActivityLogRepository activityLogRepository;
	
	@Autowired
	MysticController(CustomerRepository customerRepository,
						   UserRepository userRepository,
						   TripRepository tripRepository,
						   ActivityLogRepository activityLogRepository) {
		this.customerRepository = customerRepository;
		this.userRepository = userRepository;
		this.tripRepository = tripRepository;
		this.activityLogRepository = activityLogRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	//Trip readTrip(@PathVariable String userId, @PathVariable Long tripId) {
	List<Customer> readTrips() {
	//	this.validateUser(userId);
		return this.customerRepository.findAll();
	}
	
	/**
	 * 
	 *  Handles the adding of one trip to a customer
	 *  Handles the adding of one customer to a trip
	 *  I couldn't figure out how to do this with json:api calls
	 * 
	 * @param customerId
	 * @param tripId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{customerId}/addTrip/{tripId}")
	public void updateCustomerWithTrip(@PathVariable Long customerId, @PathVariable Long tripId) {
		updateCustomerTrip(customerId, tripId, true);
	}
	
	/**
	 * 
	 *  Handles the deletion of one trip to a customer
	 *  Handles the deletion of one customer to a trip
	 *  I couldn't figure out how to do this with json:api calls
	 * 
	 * @param customerId
	 * @param tripId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{customerId}/removeTrip/{tripId}")
	public void updateCustomerWithoutTrip(@PathVariable Long customerId, @PathVariable Long tripId) {
		updateCustomerTrip(customerId, tripId, false);
	}
	
	/**
	 *  Helper function to update customer and trip objects
	 * @param customerId
	 * @param tripId
	 * @param add
	 */
	private void updateCustomerTrip(Long customerId, Long tripId, Boolean add) {
		
		Optional<Trip> tripCheck = tripRepository.findById(tripId);
		Optional<Customer> customerCheck = customerRepository.findById(customerId);
		
		if(customerCheck.isPresent() && tripCheck.isPresent()) {		
			Customer customer = customerCheck.get();
			Trip trip = tripCheck.get();
			
			if(add) {
				customer.addTrip(trip);
				trip.addCustomer(customer);
			} else {
				customer.removeTrip(trip);
				trip.removeCustomer(customer);	
			}
			
			customerRepository.save(customer);
			tripRepository.save(trip);
		}
	}
}
