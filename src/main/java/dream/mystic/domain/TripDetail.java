package dream.mystic.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;

@Entity
@JsonApiResource(type = "trip-detail")
@SuppressWarnings("deprecation")
public class TripDetail implements Comparable<TripDetail> {
	
	@JsonApiId
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonApiToOne(opposite="tripDetails")
    @JsonApiIncludeByDefault
    @ManyToOne
    private Trip trip;
    
    private Integer sequence;
    
    private String details;
    
    private Long createdById;
    
    private Timestamp created;
    
    private Long lastModifiedBy;
    
    private Timestamp lastModified;

    private Boolean deleted;
    
    public TripDetail() {
    	// for JPA
    }
    
    public TripDetail(Customer customer, Trip trip, Integer sequence, String details) {
    	this.trip = trip;
    	created = new Timestamp(System.currentTimeMillis());
    	lastModified = created;
    	deleted = false;
    	
    	createdById = customer.getId();
    	lastModifiedBy = customer.getId();
    	
    	this.sequence = sequence;
    	this.details = details;
    	
    }
    
        
    public void deleteDetail(Long userId) {
    	if(!deleted) {
	    	deleted = true;
	    	lastModifiedBy = userId;
	    	lastModified.setTime(System.currentTimeMillis());

	    	// set to some large sentinal value
	    	sequence = Integer.MAX_VALUE;
    	}
    }
    
    @Override
	public int compareTo(TripDetail o) {
		return this.getSequence().compareTo(o.getSequence());
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

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
