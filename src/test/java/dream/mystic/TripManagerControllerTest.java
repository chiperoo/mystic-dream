package dream.mystic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import dream.mystic.domain.Customer;
import dream.mystic.domain.Trip;
import dream.mystic.domain.TripDetail;
import dream.mystic.repository.CustomerRepository;
import dream.mystic.repository.TripDetailRepository;
import dream.mystic.repository.TripRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysticDreamApplication.class)
@WebAppConfiguration
public class TripManagerControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    private String name = "batman";

    private Customer customer;
    
    private Trip trip;

    private List<TripDetail> tripDetailList = new ArrayList<>();

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private TripDetailRepository tripDetailRepository;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
	
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        // avoids foreign key constraints in this order
        this.tripDetailRepository.deleteAllInBatch();
        this.tripRepository.deleteAllInBatch();
        this.customerRepository.deleteAllInBatch();

        this.customer = customerRepository.save(new Customer(name, name + "@user.email"));
        this.trip = tripRepository.save(new Trip(this.customer, "description"));
        
        //tripDetailRepository.save(new TripDetail(customer,
		//trip, (int)counter.incrementAndGet(), "Step " + counter.get()));
        
        this.tripDetailList.add(tripDetailRepository.save(new TripDetail(this.customer, this.trip, 1, "Step 1")));
        this.tripDetailList.add(tripDetailRepository.save(new TripDetail(this.customer, this.trip, 2, "Step 2")));
    }
    
    @Test
    public void readSingleTripDetail() throws Exception {
        mockMvc.perform(get("/manage/"
                + this.tripDetailList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.tripDetailList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.sequence", is(this.tripDetailList.get(0).getSequence())))
                .andExpect(jsonPath("$.details", is("Step 1")));
    }
    
    
    
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
