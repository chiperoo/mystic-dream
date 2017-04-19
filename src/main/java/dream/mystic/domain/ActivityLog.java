package dream.mystic.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiLinksInformation;
import io.katharsis.resource.annotations.JsonApiMetaInformation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;
import io.katharsis.resource.links.LinksInformation;
import io.katharsis.resource.meta.MetaInformation;

@Entity
@JsonApiResource(type = "activityLog")
@SuppressWarnings("deprecation")
public class ActivityLog {
	
	@JsonApiId
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonApiToOne(opposite="activityLog")
    @JsonApiIncludeByDefault
    @OneToOne
	private Customer customer;
	
	@JsonApiToOne(opposite="activityLog")
    @JsonApiIncludeByDefault
    @OneToOne
	private User user;
    
    private String description;
    
    private Boolean important;
    
    private Long createdById;
    
    private Timestamp created;
    
    private Timestamp lastModified;
    
    private Long lastModifiedById;
    
	@JsonApiMetaInformation
	@Transient
	private MetaInformation metaInformation;
	
	@JsonApiLinksInformation
	@Transient
	private LinksInformation linksInformation;
    
    public ActivityLog() {
    	// for JPA
    	this.created = new Timestamp(System.currentTimeMillis());
    	this.lastModified = created;
    	this.important = false;
    }
    
    public ActivityLog(Customer customer, User user, String details) {
    	this.customer = customer;
    	this.user = user;
    	this.createdById = user.getId();
    	this.created = new Timestamp(System.currentTimeMillis());
    	this.lastModified = created;
    	this.lastModifiedById = user.getId();
    	this.important = false;
    	
    	this.description = details;
    }
    
    @PreUpdate
    private void updateLastModified() {
    	setLastModified(new Timestamp(System.currentTimeMillis()));
    	if(lastModifiedById == null)
    		lastModifiedById = createdById;
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

	public Boolean getImportant() {
		return important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
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

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}	
	
	public MetaInformation getMetaInformation() {
		return metaInformation;
	}

	public void setMetaInformation(MetaInformation metaInformation) {
		this.metaInformation = metaInformation;
	}

	public LinksInformation getLinksInformation() {
		return linksInformation;
	}

	public void setLinksInformation(LinksInformation linksInformation) {
		this.linksInformation = linksInformation;
	}
}
