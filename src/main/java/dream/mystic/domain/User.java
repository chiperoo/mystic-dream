package dream.mystic.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiLinksInformation;
import io.katharsis.resource.annotations.JsonApiMetaInformation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.links.LinksInformation;
import io.katharsis.resource.meta.MetaInformation;

@Entity
@JsonApiResource(type = "user")
@SuppressWarnings("deprecation")
public class User extends Person {

	// Karthasis might not fully support inheritance and 
	// different sets of activityLogs and different annotations
	@JsonApiToMany(opposite = "user")
    @OneToMany(mappedBy = "user")
	private List<ActivityLog> activityLog = new ArrayList<ActivityLog>();
	
	public User() { 
		// for JPA 
	}
	
	public User(String name, String emailAddress) {
		setName(name);
		setEmailAddress(emailAddress);
	}
	
    ///////////////////////////////////
    // Getters / setters
    ///////////////////////////////////
	public List<ActivityLog> getActivityLog() {
		return activityLog;
	}

	public void setActivityLog(List<ActivityLog> activityLog) {
		this.activityLog = activityLog;
	}
}
