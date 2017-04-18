package dream.mystic.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;

@Entity
@JsonApiResource(type = "trip")
@SuppressWarnings("deprecation")
public class Trip {
	
	@Id
	@GeneratedValue
	@JsonApiId
	private Long id;
    
    private Timestamp created;
	
    private String description;
    
    @JsonApiToMany(opposite="trips")
    //@JsonApiIncludeByDefault
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Customer> customers = new HashSet<Customer>();
    
    public Trip() {
    	// for JPA
    	this.created = new Timestamp(System.currentTimeMillis());
    }
    
    public Trip(String description) {
    	this.description = description;
    	this.created = new Timestamp(System.currentTimeMillis());
    }
        

    // one-offs for the customers
    public Boolean addCustomer(Customer customer) {
    	return customers.add(customer);
    }
    
    public void removeCustomer(Customer customer) {
    	customers.remove(customer);
    }
    
    ///////////////////////////////////
    // Getters / setters
    ///////////////////////////////////
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
    
}
