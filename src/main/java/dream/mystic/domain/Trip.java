package dream.mystic.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
//import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.JsonApiToOne;
//import io.katharsis.resource.annotations.LookupIncludeBehavior;
//import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = "trip")
@Entity
@SuppressWarnings("deprecation")
public class Trip {
	
	@Id
	@GeneratedValue
	@JsonApiId
	private Long id;
	
	private Long createdById;
    
    private Timestamp created;
	
    @JsonApiToOne(opposite="trip")
    @JsonApiIncludeByDefault
    @ManyToOne
    private Customer customer;
    
    private String description;
    
//    @JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.ONLY_ID)
	@JsonApiToMany(opposite = "trip")
    @OneToMany(mappedBy = "trip")
	private List<TripDetail> tripDetails = new ArrayList<TripDetail>();

    public Trip() {
    	// for JPA
    	this.created = new Timestamp(System.currentTimeMillis());
    }
    
    public Trip(Customer customer, String description) {
    	this.customer = customer;
    	this.description = description;
    	this.createdById = customer.getId();
    	this.created = new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * 
     * @param td
     * @return
     */
    public List<TripDetail> addTripDetail(TripDetail td) {
    	
    	tripDetails.add(td);
    	Collections.sort(tripDetails);
    	reorderTripDetails();
    	return tripDetails;
    }
    
    public List<TripDetail> deleteTripDetail(TripDetail td, Long userId) {
    	int index = tripDetails.indexOf(td);
    	if(index >= 0) {
    		tripDetails.remove(td);
    		td.deleteDetail(userId);
    		reorderTripDetails();
    	}
    	
    	return tripDetails;
    }
    
    private void reorderTripDetails() {
    	
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

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TripDetail> getTripDetails() {
		return tripDetails;
	}

	public void setTripDetails(List<TripDetail> tripDetails) {
		this.tripDetails = tripDetails;
	}
    
}
