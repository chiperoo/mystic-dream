package dream.mystic.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;

@JsonApiResource(type = "trip")
@Entity
@SuppressWarnings("deprecation")
public class Trip {
	
	@Id
	@GeneratedValue
	@JsonApiId
	private Long id;
    
    private Timestamp created;
	
    private String description;
    
    @JsonApiToMany(opposite="trip")
    @JsonApiIncludeByDefault
    @ManyToMany
    private Set<Customer> customer = new HashSet<Customer>();
    
//    @JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.ONLY_ID)
//	@JsonApiToMany(opposite = "trip")
//    @OneToMany(mappedBy = "trip")
//	private Set<ActivityLog> activityLog = new HashSet<ActivityLog>();

    public Trip() {
    	// for JPA
    	this.created = new Timestamp(System.currentTimeMillis());
    }
    
    public Trip(String description) {
    	this.description = description;
    	this.created = new Timestamp(System.currentTimeMillis());
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

	public Set<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(Set<Customer> customer) {
		this.customer = customer;
	}
    
}
