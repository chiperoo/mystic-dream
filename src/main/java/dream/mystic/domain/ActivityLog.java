package dream.mystic.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;

@Entity
@JsonApiResource(type = "activityLog")
@SuppressWarnings("deprecation")
public class ActivityLog {
	
	@JsonApiId
	@Id
	@GeneratedValue
	private Long id;
	
//	@JsonApiToOne(opposite="activityLog")
//    @JsonApiIncludeByDefault
//    @OneToOne
//    private Trip trip;
	
	@JsonApiToOne(opposite="activityLog")
    @JsonApiIncludeByDefault
    @OneToOne
	private Customer customer;
	
	@JsonApiToOne(opposite="activityLog")
    @JsonApiIncludeByDefault
    @OneToOne
	private User user;
    
    private String description;
    
    private Long createdById;
    
    private Timestamp created;
    
    private Timestamp lastModified;
    
    public ActivityLog() {
    	// for JPA
    	this.created = new Timestamp(System.currentTimeMillis());
    	this.lastModified = created;
    }
    
    public ActivityLog(Customer customer, User user, String details) {
    	this.customer = customer;
//    	this.trip = trip;
    	this.user = user;
    	createdById = user.getId();
    	this.created = new Timestamp(System.currentTimeMillis());
    	this.lastModified = created;
    	
    	this.description = details;
    }
    
    @PreUpdate
    private void updateLastModified() {
    	setLastModified(new Timestamp(System.currentTimeMillis()));
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

//	public Trip getTrip() {
//		return trip;
//	}
//
//	public void setTrip(Trip trip) {
//		this.trip = trip;
//	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}
	
}
