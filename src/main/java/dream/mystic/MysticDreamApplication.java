package dream.mystic;

import java.util.Arrays;
import java.util.HashMap;
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
import dream.mystic.domain.TripDetail;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.TripDetailRepository;
import dream.mystic.repository.TripRepository;

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
		// Add all resources (i.e. Project and Task)
		System.out.println("***************************** registry size: " + resourceRegistry.getResources().size());
		for (RegistryEntry entry : resourceRegistry.getResources()) {
			result.put(entry.getResourceInformation().getResourceType(), resourceRegistry.getResourceUrl(entry.getResourceInformation()));
		}
		return result;
	}
	

	public static void main(String[] args) {
		SpringApplication.run(MysticDreamApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(CustomerRepository customerRepository,
			TripRepository tripRepository, TripDetailRepository tripDetailRepository) {
		
		AtomicLong counter = new AtomicLong();
		
		return (evt) -> Arrays.asList(
				"bruce,clark,barry,hal".split(","))
				.forEach(
						a -> {
							counter.set(0L);
							Customer customer = customerRepository.save(new Customer(a,
									a + "@fake.email"));
							Trip trip = tripRepository.save(new Trip(customer,
									"Justice League Party"));
							tripDetailRepository.save(new TripDetail(customer,
									trip, (int)counter.incrementAndGet(), "Step " + counter.get()));
							tripDetailRepository.save(new TripDetail(customer,
									trip, (int)counter.incrementAndGet(), "Step " + counter.get()));
							
						});
		
//		return args -> {
//			
//		};
	}
	

}
