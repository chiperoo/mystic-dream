package dream.mystic.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@Entity
@JsonApiResource(type = "customer")
@SuppressWarnings("deprecation")
public class Customer {
	
	@Id
	@GeneratedValue
	@JsonApiId
	private Long id;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String emailAddress;
	
	@JsonApiToMany(opposite = "customer")
//	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.ONLY_ID)
	@OneToMany(mappedBy = "customer")
	private Set<Trip> trips = new HashSet<Trip>();
	
	
	Customer() {}
	
	public Customer(String name, String emailAddress) {
		this.name = name;
		this.emailAddress = emailAddress;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<Trip> getTrips() {
		return trips;
	}

	public void setTrips(Set<Trip> trips) {
		this.trips = trips;
	}
}
