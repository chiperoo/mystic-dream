package dream.mystic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.TripRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysticDreamApplication.class)
@WebAppConfiguration
public class MysticControllerTest {

    private MockMvc mockMvc;
  
    private String name = "batman";

    private Customer customer;
    
    private Trip trip;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
	
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        // use these objects for the tests
        this.customer = customerRepository.save(new Customer(name, name + "@user.email"));
        this.trip = tripRepository.save(new Trip("description"));
    }
    
    @Test
    public void signUpCustomerForNewTrip() throws Exception {
        // sign up for new trip via RESTful call
    	mockMvc.perform(post("/mystic/"
                + this.customer.getId() + "/addTrip/"  
                + this.trip.getId()));
        
    	// check database if customer and trip records reflect the new signup
        Optional<Customer> customerTest = customerRepository.findById(this.customer.getId());
        Customer customerCopy = customerTest.get();
        
        Optional<Trip> tripTest = tripRepository.findById(this.trip.getId());
        Trip tripCopy = tripTest.get();
        
        Assert.assertEquals(1, customerCopy.getTrips().size());
        Assert.assertEquals(1, tripCopy.getCustomers().size());
    }
    
    
    @Test
    public void removeCustomerFromNewTrip() throws Exception {
        // remove trip from customer via RESTful call
    	mockMvc.perform(post("/mystic/"
                + this.customer.getId() + "/removeTrip/"  
                + this.trip.getId()));
        
    	// check database if customer and trip records reflect the removal
        Optional<Customer> customerTest = customerRepository.findById(this.customer.getId());
        Customer customerCopy = customerTest.get();
        
        Optional<Trip> tripTest = tripRepository.findById(this.trip.getId());
        Trip tripCopy = tripTest.get();
        
        Assert.assertEquals(0, customerCopy.getTrips().size());
        Assert.assertEquals(0, tripCopy.getCustomers().size());
    }
}
