package dream.mystic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dream.mystic.domain.Trip;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.ActivityLogRepository;
import dream.mystic.repository.TripRepository;

@RestController
@RequestMapping("/manage")
public class TripManagerController {

	private final CustomerRepository customerRepository;
	private final TripRepository tripRepository;
	private final ActivityLogRepository activityLogRepository;
	
	@Autowired
	TripManagerController(CustomerRepository customerRepository,
						   TripRepository tripRepository,
						   ActivityLogRepository activityLogRepository) {
		this.customerRepository = customerRepository;
		this.tripRepository = tripRepository;
		this.activityLogRepository = activityLogRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{tripId}")
	//Trip readTrip(@PathVariable String userId, @PathVariable Long tripId) {
	public @ResponseBody Trip readTrip(@PathVariable Long tripId) {
	//	this.validateUser(userId);
		return this.tripRepository.findOne(tripId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	//Trip readTrip(@PathVariable String userId, @PathVariable Long tripId) {
	List<Trip> readTrips() {
	//	this.validateUser(userId);
		return this.tripRepository.findAll();
	}
}
