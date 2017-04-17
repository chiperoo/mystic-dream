package dream.mystic.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.katharsis.resource.annotations.JsonApiId;

@MappedSuperclass
public abstract class Person {

	@Id
	@GeneratedValue
	@JsonApiId
	private Long id;
		
	private String name;
	
	private String emailAddress;
	
	
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
}
