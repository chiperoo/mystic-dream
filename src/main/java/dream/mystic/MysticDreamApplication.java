package dream.mystic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;
//import io.katharsis.spring.boot.KatharsisConfigV2;
import io.katharsis.spring.boot.v3.KatharsisConfigV3;
import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import dream.mystic.domain.User;
import dream.mystic.domain.ActivityLog;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.ActivityLogRepository;
import dream.mystic.repository.TripRepository;
import dream.mystic.repository.UserRepository;

@Configuration
@RestController
@SpringBootApplication
@Import({ KatharsisConfigV3.class, JpaConfig.class, ModuleConfig.class })
public class MysticDreamApplication {
	
	@Autowired
	private ResourceRegistry resourceRegistry;

	@RequestMapping("/resourcesInfo")
	public Map<?, ?> getResources() {
		Map<String, String> result = new HashMap<>();
		// Add all resources for API exposure
		for (RegistryEntry entry : resourceRegistry.getResources()) {
			result.put(entry.getResourceInformation().getResourceType(), resourceRegistry.getResourceUrl(entry.getResourceInformation()));
		}
		return result;
	}
	

	public static void main(String[] args) {
		SpringApplication.run(MysticDreamApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(CustomerRepository customerRepository, UserRepository userRepository,
			TripRepository tripRepository, ActivityLogRepository activityLogRepository) {

		AtomicLong counter = new AtomicLong();
		
		// create default trips
		List<Trip> tripList = new ArrayList<Trip>();
		Arrays.asList(
				"London,Paris,Tokyo,Sydney".split(","))
				.forEach(
						a -> {
							Trip trip = tripRepository.save(new Trip(a));
							tripList.add(trip);
						});
		
		// create default admin user
		User user = userRepository.save(new User("admin", "admin@admin.com"));

		// load up test data
		return (evt) -> Arrays.asList(
				"bruce,clark,barry,hal".split(","))
				.forEach(
						a -> {
							Customer customer = customerRepository.save(new Customer(a,
									a + "@fake.email"));
							// grab a unique trip
							Trip trip = tripList.get((int)counter.getAndIncrement());
							
							// add base activity log entries
							activityLogRepository.save(new ActivityLog(customer,
									user, "Found trip to " + trip.getDescription()));
							activityLogRepository.save(new ActivityLog(customer,
									user, "Booked to " + trip.getDescription()));
							
						});
	}
	

}
