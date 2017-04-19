package dream.mystic.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.links.LinksInformation;
import io.katharsis.resource.meta.MetaInformation;


@Entity
@JsonApiResource(type = "customer")
@SuppressWarnings("deprecation")
public class Customer extends Person{
	
	// Karthasis might not fully support inheritance and 
	// different sets of activityLogs and different annotations
	@JsonApiToMany(opposite = "customer")
    @OneToMany(mappedBy = "customer")
	private List<ActivityLog> activityLog = new ArrayList<ActivityLog>();
	
	@JsonApiToMany(opposite = "customers")
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "customers")
	private Set<Trip> trips = new HashSet<Trip>();
	
	public Customer() { 
		// for JPA 
	}
	
	public Customer(String name, String emailAddress) {
		setName(name);
		setEmailAddress(emailAddress);
	}

	
	// one-offs for the set
	public Boolean addTrip(Trip trip) {
		return trips.add(trip);
		
	}
	
	public void removeTrip(Trip trip) {
		trips.remove(trip);
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
	
	public Set<Trip> getTrips() {
		return trips;
	}

	public void setTrips(Set<Trip> trips) {
		this.trips = trips;
	}	
}
