# Mystic Dream: Customer API

## Table of Contents
1. [Overview](#overview)
2. [Code Template](#code-template)
3. [Dependencies](#dependencies)
4. [Running with Maven](#running-with-maven)
5. [Calls](#calls)
   1. [View All JSON API calls](#view-all-json-api-calls)
   2. [Get All Customers](#get-all-customers)
   3. [Get Specific Customers](#get-specific-customers)
   4. [Create New Customer](#create-new-customer)
   5. [Update Specific Customer](#update-specific-customer)
   6. [Create New Trip](#create-new-trip)
   7. [Get All Internal Users](#get-all-internal-users)
   8. [Create New Internal User](#create-new-internal-user)
   9. [Get All Activity Log Records](#get-all-activity-log-records)
   10. [Create New Activity Log Record](#create-new-activity-log-record)
   11. [Signing Customer For a Trip](#signing-customer-for-a-trip)
   12. [Flag an Activity Log Record as Important](#flag-an-activity-log-record-as-important)
   13. [Remove a Trip From a Customer](#remove-a-trip-from-a-customer)
   14. [Get All Activity Log Records For Specific Customer](#get-all-activity-log-records-for-specific-customer)
   15. [View All Customers Going on a Specific Trip](#view-all-customers-going-on-a-specific-trip)
   16. [View All Trips a Customer is Going On](#view-all-trips-a-customer-is-going-on)
   17. [View Activity Log Created By Specific User](#view-activity-log-created-by-specific-user)
6. [Filtering and Sorting](#filtering-and-sorting)
7. [Implementation](#implementation)
   1. [Class Structure](#class-structure)
8. [Code](#code)
   1. [Spring Originated Code](#spring-originated-code)
   2. [Files Required for Katharsis](#files-required-for-Katharsis)
   3. [Pom.xml](#pomxml)
   4. [Application.properties](#applicationproperties)
   5. [Classes/Models/Resources](#classes--models--resources)
   6. [IDs](#ids)
   7. [Meta Information and Links](#meta-information-and-links)
   8. [Relationships](#relationships)
   9. [ResourceRepositories](#resourcerepositories)
   10. [RelationshipRepositories](#relationshiprepositories)
      1. [Note](#note)
   11. [Main class](#main-class)
   12. [Design Notes](#design-notes)
9. [Testing](#testing)
   1. [JSON API Tests](#json-api-tests)
      1. [Pom.xml Test Changes](#pomxml-test-changes)
   2. [Non-JSON API Tests](#non-json-api-tests)


---

## Overview
The support team at Mystic Dream is streamlining their customer logistics. With a spike in new customers, it is more difficult to manage and track all the steps involved in scheduling customer trips. An interface has been built.

The goal of this project is to build two APIs that:
 
* Manage customer information
* Manage and track steps in the planning process

## Code Template
This code borrows heavily from a [Spring REST tutorial](http://spring.io/guides/tutorials/bookmarks/) as well as the [Katharsis Spring example](https://github.com/katharsis-project/katharsis-framework/tree/master/katharsis-examples/spring-boot-simple-example).

## Dependencies
The code uses: 

* [Spring Boot](https://projects.spring.io/spring-boot/)
* H2
* [Maven](https://maven.apache.org/index.html)
* [Katharsis](http://katharsis.io/) (to implement JSON API)

## Running with Maven
```
$ mvn clean spring-boot:run
```

## Calls
I found [Postman](https://www.getpostman.com/) invaluable for testing the calls. It is available as a Chrome extension or Mac/Windows/Linux application.

#### View All JSON API Calls
```
GET http://localhost:8080/resourcesInfo
```

#### Get All Customers
```
GET http://localhost:8080/api/customer/

# Sorted by name ASC
GET http://localhost:8080/api/customer/?sort=name

# Sorted by name DESC
GET http://localhost:8080/api/customer/?sort=-name

# Specify pagination
http://localhost:8080/api/customer?page[limit]=2
```

#### Get Specific Customers
```
# Get customer id 1
GET http://localhost:8080/api/customer/1

# Get customers id 1 and 3
GET http://localhost:8080/api/customer/1,3
```

#### Create New Customer
```
POST http://localhost:8080/api/customer/

# body; type: json
  {
  	"data": {
  		"type": "customer",
  		"attributes": {
  			"emailAddress": "diana@fake.email",
  			"name": "diana"
  		}
  	}
  }
```

#### Update Specific Customer
```
# Update customer id 5	
PATCH http://localhost:8080/api/customer/5

# body; type: json
  {
    "data": {
      "type": "customer",
      "attributes": {
        "name": "wonder woman"
      }
    }
  }
```

#### Create New Trip
```
POST http://localhost:8080/api/trip

# body; type: json
  {
    "data": {
      "type": "trip",
      "attributes": {
        "description": "Gotham City"
      }
    }
  }
```

#### Get All Internal Users
```
GET http://localhost:8080/api/user
```

#### Create New Internal User
```
POST http://localhost:8080/api/user/

# body; type: json
  {
  	"data": {
  		"type": "user",
  		"attributes": {
  			"emailAddress": "owl@admin.email",
  			"name": "Owl"
  		}
  	}
  }
```

#### Get All Activity Log Records
```
GET http://localhost:8080/api/activityLog
```

#### Create New Activity Log Record
``` 
# Create activity log entry for customer 3; by user 2
POST http://localhost:8080/api/activityLog

# body; type: json
  {
    "data": {
      "type": "activityLog",
      "attributes": {
          "description": "Customer called wanting a trip",
          "createdById": "2"
      },
      "relationships": {
      	"user" : {
      		"data": {
      			"id": "2",
      			"type": "user"
      		}
      	},
      	"customer" : {
          "data": {
            "id": "3",
            "type": "customer"
          }
        }
    	}
  	}
  }
```

#### Signing Customer For a Trip
```
# Customer 3 signs up for a trip; handled by admin user 2
POST http://localhost:8080/api/activityLog

# body; type: json
  {
    "data": {
      "type": "activityLog",
      "attributes": {
          "description": "Customer signed up for trip to Gotham City",
          "createdById": "2"
      },
      "relationships": {
      	"user" : {
      		"data": {
      			"id": "2",
      			"type": "user"
      		}
      	},
      	"customer": {
          	"data": {
          		"id": "3",
              "type": "customer"
       		}
  		}
      }
  	}
  }

# Update customer and trip records to reflect trip
# Note that this is a different url format
POST http://localhost:8080/mystic/3/addTrip/5
```

#### Flag an Activity Log Record as Important
```
# Flag an activity log record as important
PATCH http://localhost:8080/api/activityLog/2

# body; type: json
 {
    "data": {
      "type": "activityLog",
      "attributes": {
        "important": "true"
      }
    }
  }
```

#### Remove a Trip From a Customer
```
#  Remove a trip from customer 3
POST http://localhost:8080/api/activityLog

# body; type: json
  {
    "data": {
      "type": "activityLog",
      "attributes": {
          "description": "Customer removed trip to Gotham City",
          "createdById": "2"
      },
      "relationships": {
      	"user" : {
      		"data": {
      			"id": "2",
      			"type": "user"
      		}
      	},
      	"customer": {
          	"data": {
          		"id": "3",
              "type": "customer"
       		}
  		}
    	}
  	}
  }

# Update customer and trip records to reflect trip
# Note that this is a different url format
POST http://localhost:8080/mystic/3/removeTrip/5
```

#### Get All Activity Log records For Specific Customer
```
# Get entire activity log for customer 3
GET http://localhost:8080/api/customer/3/relationships/activityLog
```

#### View All Customers Going on a Specific Trip
```
# View all customers going on trip 4
GET http://localhost:8080/api/trip/4/customers
```

#### View All Trips a Customer is Going on
```
GET http://localhost:8080/api/customer/2/trips
```

#### View Activity Log Created By Specific User
```
#  View the activity logs created by admin user 1, sorted by lastModified DESC
GET http://localhost:8080/api/activityLog/?filter[activityLog][user][id]=1&sort=-lastModified
```

### Filtering, Sorting, and Pagination
As shown in some of the sample calls, [filtering](http://katharsis-jsonapi.readthedocs.io/en/latest/user-docs.html#filtering), [sorting](http://katharsis-jsonapi.readthedocs.io/en/latest/user-docs.html#sorting), and [pagination](http://katharsis-jsonapi.readthedocs.io/en/latest/user-docs.html#pagination) is built in.

Filters use the `filter` parameter.

* `GET http://localhost:8080/api/activityLog?filter[important]=true`
* `GET http://localhost:8080/api/activityLog?filter[important][EQ]=true`
* `GET http://localhost:8080/api/activityLog?filter[description][LIKE]=syd`
* `GET http://localhost:8080/api/activityLog?filter[activityLog][important]=true`

Sorting uses the `sort` parameter.

* `GET http://localhost:8080/api/customer?sort=name,-id`
* `GET http://localhost:8080/api/trip?sort=-created`

Pagination uses the `page` parameter.

* `GET http://localhost:8080/api/activityLog?page[limit]=2`

The `first`, `last`, `next`, and `prev` links are also provided. In the sample call above, these are the provided links:

```json
"links": {
    "first": "http://localhost:8080/api/activityLog/?page[limit]=2",
    "last": "http://localhost:8080/api/activityLog/?page[limit]=2&page[offset]=6",
    "next": "http://localhost:8080/api/activityLog/?page[limit]=2&page[offset]=2",
    "prev": null
  }
```


## Implementation

### Class Structure
![class diagram](./images/mystic.png)

**Customer** - The customers of Mystic Dream.

**User** - Internal users. The employees of Mystic Dream.

**Trip** - The list of trips that customers go on.

**ActivityLog** - The activity log of the customers, as entered by the users. These entries record all interactions with customers.

## Code
Assuming that a Spring application is created from the [Spring REST tutorial](http://spring.io/guides/tutorials/bookmarks/), this document will focus on the changes needed to integrate Karthasis and JSON API.

### Spring Originated Code
```
dream.mystic/
    MysticDreamApplication.java
dream.mystic.controller/
    MysticController.java
dream.mystic.domain/
    ActivityLog.java
    Customer.java
    Person.java
    Trip.java
    User.java
dream.mystic.repository/
    ActivityLogRepository.java
    CustomerRepository.java
    TripRepository.java
    UserRepository.java
```

### Files Required for Katharsis
```
dream.mystic.repository.jsonapi/
    ActivityLogResourceRepositoryImpl.java
    ActivityLogToCustomerRelationshipRepository.java
    ActivityLogToUserRelationshipRepository.java
    CustomerResourceRepository.java
    CustomerResourceRepositoryImpl.java
    CustomerToActivityLogRelationshipRepository.java
    CustomerToTripRelationshipRepository.java
    TripResourceRepositoryImpl.java
    TripToCustomerRelationshipRepository.java
    UserResourceRepository.java
    UserResourceRepositoryImpl.java
    UserToActivityLogRelationshipRepository.java
```

The files in the `jsonapi` package breakdown as:

* `<X>ResourceRepository.java` file is an interface to generate meta information and links. This can be accomplished via annotations, but for the purposes of unit testing, the KatharsisClient needs to connect to an interface.
* `<X>ResourceRepositoryImpl.java` is the Katharsis equivalent to the JPA `<X>Repository.java` files. Katharsis uses these files to publish API operations. For the files that only exist as an `Impl`, the meta information and links are generated via annotations in the domain object.
* `<X>To<Y>RelationshipRepository.java` files handle the unidirectional relationships between resources X and Y. This is required when there is an annotation `@JsonApiToOne` or `@JsonApiToMany` of field Y in the X class. 
    + Because `<X>To<Y>` is unidirectional, there also needs to be a `<Y>To<X>RelationshipRepository.java` file to handle the other side of the relationship.

### Pom.xml
Add the following dependencies to the `pom.xml` file.

```xml
    <dependency>
      <groupId>io.katharsis</groupId>
      <artifactId>katharsis-spring</artifactId>
      <version>3.0.1</version>
    </dependency>

    <dependency>
      <groupId>io.katharsis</groupId>
      <artifactId>katharsis-validation</artifactId>
      <version>3.0.1</version>
    </dependency>
    
    <dependency>
      <groupId>io.katharsis</groupId>
      <artifactId>katharsis-jpa</artifactId>
      <version>3.0.1</version>
    </dependency>
```

### Application.properties
This defines many of Katharsis' properties.

```
katharsis.resourcePackage=io.katharsis.example.springboot.simple.domain
katharsis.domainName=http://localhost:8080
katharsis.pathPrefix=/api
katharsis.default-page-limit=25
katharsis.jpa.enabled=false
```

### Classes / Models / Resources
Each class needs to have a `@JsonApiResource` annotation. This defines a resource and is used in the URLs and type field in all passed JSONs.

```java
@Entity
@JsonApiResource(type = "customer")
@SuppressWarnings("deprecation")
public class Customer extends Person{
...
```

### IDs
Within each Class/Model/Resource, an ID needs to be annotated with `@JsonApiId`. This enables a resource to be properly identified.

```java
@Entity
@JsonApiResource(type = "trip")
@SuppressWarnings("deprecation")
public class Trip {
  
  @Id
  @GeneratedValue
  @JsonApiId
  private Long id;
  ...
```

### Meta Information and Links
This provides the object count and pagination links (first, next, previous, last) when a page limit is set. In the `application.properties` snippet above, the default page-size is set to 25.

Within each Class/Model/Resource (that does not have a corresponding `ResourceRepository.java` interface), meta information and links are generated via annotations.

```java
  @JsonApiMetaInformation
  @Transient
  private MetaInformation metaInformation;
  
  @JsonApiLinksInformation
  @Transient
  private LinksInformation linksInformation;
```

Note the use of the `@Transient` annotation that keeps these fields from being stored in the database.

### Relationships
If a Class/Resource has an association with another resource, the field needs to have an annotation of `@JsonApiToMany` or `@JsonApiToOne`.

```java
@Entity
@JsonApiResource(type = "customer")
@SuppressWarnings("deprecation")
public class Customer extends Person{
  
  // Karthasis might not fully support inheritance and 
  // different sets of activityLogs
  @JsonApiToMany(opposite = "customer")
  @OneToMany(mappedBy = "customer")
  private List<ActivityLog> activityLog = new ArrayList<ActivityLog>();
  
  @JsonApiToMany(opposite = "customers")
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "customers")
  private Set<Trip> trips = new HashSet<Trip>();
```

_Note that in the snippet above, `FetchType.EAGER` was set instead of `FetchType.LAZY`. This was a workaround to deal with the many-to-many relationship._

```java
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
```

### ResourceRepositories
As mentioned above, ResourceRepositories are used to publish API operations. It is also important to annotate each class with `@Component` to expose the resource via reflection.

```java
@Component
public class TripResourceRepositoryImpl extends ResourceRepositoryBase<Trip,Long> {

  @Autowired
  private TripRepository tripRepository;
  
  public TripResourceRepositoryImpl() {
    super(Trip.class);
  }

  @Override
    public synchronized <S extends Trip> S save(S trip) {
            tripRepository.save(trip);
            return trip;
  }
  
  @Override
  public synchronized Trip findOne(Long tripId, QuerySpec arg0) {
    Trip trip =  tripRepository.findOne(tripId);
    if(trip == null) {
      throw new ResourceNotFoundException("Trip record not found");
    }
    return trip;
  }
  
  @Override
  public synchronized ResourceList<Trip> findAll(QuerySpec arg0) {
    return arg0.apply(tripRepository.findAll());
  }

}
```

Because this application uses an in-memory database, the operations to retrieve or save interact with the JPA classes.

### RelationshipRepositories
As mentioned above, RelationshipRepositories are used to handle the relationships between resources that were annotated with `@JsonApiToMany` or `@JsonApiToOne`. Just like with the ResourceRepositories, the `@Component` annotation is required to expose the resource via reflection.

A simple one-to-one or one-to-many implementation.

```java
@Component
public class ActivityLogToUserRelationshipRepository extends RelationshipRepositoryBase<ActivityLog, Long, User, Long> {

  public ActivityLogToUserRelationshipRepository() {
    super(ActivityLog.class, User.class);
  }

}
```

The implementation is much more verbose for a many-to-many relationship.

```java
@Component
public class CustomerToTripRelationshipRepository extends RelationshipRepositoryBase<Customer, Long, Trip, Long> {

  @Autowired
  private CustomerRepository customerRepository;
  
  @Autowired 
  private TripRepository tripRepository;
  
  public CustomerToTripRelationshipRepository() {
    super(Customer.class, Trip.class);
  }

  @Override
    public void setRelation(Customer customer, Long tripId, String fieldName) {
        // not for many-to-many
    }

    @Override
    public void setRelations(Customer customer, Iterable<Long> tripIds, String fieldName) {
        final Set<Trip> trips = new HashSet<Trip>();
        trips.addAll(tripRepository.findAll(tripIds));
        customer.setTrips(trips);
        customerRepository.save(customer);
    }

    @Override
    public void addRelations(Customer customer, Iterable<Long> tripIds, String fieldName) {
        final Set<Trip> trips = customer.getTrips();
        trips.addAll(tripRepository.findAll(tripIds));
        customer.setTrips(trips);
        customerRepository.save(customer);
    }

    @Override
    public void removeRelations(Customer customer, Iterable<Long> tripIds, String fieldName) {
        final Set<Trip> trips = customer.getTrips();
        trips.removeAll(tripRepository.findAll(tripIds));
        customer.setTrips(trips);
        customerRepository.save(customer);
    }

    @Override
    public ResourceList<Trip> findManyTargets(Long sourceId, String fieldName, QuerySpec args0) {
        final Customer customer = customerRepository.findOne(sourceId);
        return args0.apply(customer.getTrips());
    }
    
}
```

#### Note
I had trouble implementing the POST/PATCH calls via JSON API to update the many-to-many relationship. In this particular project, a customer can go on many different trips. A trip can be taken by many different customers. When a customer signs up for a trip, I need to update this new join relationship instance.

The workaround was to do this in code via a RESTful Spring call. This is why the Calls section noted a different URL for specific operations.

```java
@RestController
@RequestMapping("/mystic")
public class MysticController {
...
  /**
   * 
   *  Handles the deletion of one trip to a customer
   *  Handles the deletion of one customer to a trip
   * @param customerId
   * @param tripId
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "/{customerId}/addTrip/{tripId}")
  public void updateCustomerWithTrip(@PathVariable Long customerId, @PathVariable Long tripId) {
    updateCustomerTrip(customerId, tripId, true);
  }
  
  /**
   * 
   *  Handles the deletion of one trip to a customer
   *  Handles the deletion of one customer to a trip
   * @param customerId
   * @param tripId
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "/{customerId}/removeTrip/{tripId}")
  public void updateCustomerWithoutTrip(@PathVariable Long customerId, @PathVariable Long tripId) {
    updateCustomerTrip(customerId, tripId, false);
  }
  
  /**
   *  Helper function to update customer and trip objects
   * @param customerId
   * @param tripId
   * @param add
   */
  private void updateCustomerTrip(Long customerId, Long tripId, Boolean add) {
    
    Optional<Trip> tripCheck = tripRepository.findById(tripId);
    Optional<Customer> customerCheck = customerRepository.findById(customerId);
    
    if(customerCheck.isPresent() && tripCheck.isPresent()) {    
      Customer customer = customerCheck.get();
      Trip trip = tripCheck.get();
      
      if(add) {
        customer.addTrip(trip);
        trip.addCustomer(customer);
      } else {
        customer.removeTrip(trip);
        trip.removeCustomer(customer);  
      }
      
      customerRepository.save(customer);
      tripRepository.save(trip);
    }
  }
...
```

### Main class
The main class needs to also be ammended to tie everything together. The primary changes include the `@Import` annotation with `KatharsisConfigV3.class`. The `getResources()` publishes all the available JSON API calls at `GET http://localhost:8080/api/resourcesInfo`.

```java
@Configuration
@RestController
@SpringBootApplication
@Import({ KatharsisConfigV3.class })
public class MysticDreamApplication {
  
  @Autowired
  private ResourceRegistry resourceRegistry;

  @RequestMapping("/resourcesInfo")
  public Map<?, ?> getResources() {
    Map<String, String> result = new HashMap<>();
    // Add all resources for API exposure
    for (RegistryEntry entry : resourceRegistry.getResources()) {
      result.put(entry.getResourceInformation().getResourceType(), resourceRegistry.getResourceUrl(entry.getResourceInformation()));
    }
    return result;
  }
  
  // Spring Boot 
  public static void main(String[] args) {
    SpringApplication.run(MysticDreamApplication.class, args);
  }
```

### Design notes
* The unit tests for JSON API rely on [Katharsis Client](https://github.com/katharsis-project/katharsis-framework/tree/master/katharsis-client) which requires an interface and not a class. Even though the [documentation](http://katharsis-jsonapi.readthedocs.io/en/stable/user-docs.html#meta-information) says that an interface is no longer required, the Katharsis Client relies on the older implementation method. Any class that has the newer annotations will error out when the Katharsis Client tries to connect.
* Person is an abstract class, which Customer and User inherit from. Although Customer and User each have a list of ActivityLogs, the different annotations (mapping id) forced the field into the concrete classes.
   * Customer and User do not use the [annotated meta information and links](#meta-information-and-links) because of the unit tests. The other domain objects (Trip, ActivityLog) do use the annotated meta information and links. If the annotated fields were allowed by the Katharsis Client, the annotated fields would have been in the abstract Person class.
* Delete operations were intentionally set to forbidden, though it is easy enough to modify this behavior to implement a soft-delete by creating and updating a `deleted` flag within each domain object.
```java
# TripResourceRepositoryImpl.java
...
  @Override
  public void delete(Long tripId) {
    throw new ForbiddenException("Delete is not allowed");
  }
```
* As mentioned in the [Relationship Repository Note](#note), updating the many-to-many relationship between Customer and Trip got overly complicated via JSON API compatible calls. The workaround was to use a RESTful Spring call and update the relationships via Java code.
* ActivityLog has a `lastModified` timestamp field and a `lastModifiedById` field. The `lastModifiedById` needs to be in the JSON body (for update), as there is no account security to know who is accessing these URLs. With proper credentialling, this can be automatically calculated. 
   * `lastModified` is automatically updated via this code
   ```java
    @PreUpdate
    private void updateLastModified() {
      setLastModified(new Timestamp(System.currentTimeMillis()));
      if(lastModifiedById == null)
        lastModifiedById = createdById;
    }
   ```


## Testing
There are two sets of unit tests: one for the JSON API calls, one for the non-JSON API calls.

### JSON API Tests
These tests cover:

* Connecting via the Katharsis Client
* For each class type (Customer, User, Trip, Activity Log):
   * Finding one:
   * Finding an invalid:
   * Finding many:
   * Deleting
   * Creating
   * Updating

#### Pom.xml Test Changes
A few additional dependencies were needed to support the unit tests. One tricky addition was Rest Assured, which had a [conflicting version of asm](https://github.com/rest-assured/rest-assured/wiki/FAQ#faq).

```xml
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
    
    <dependency>
      <groupId>org.springframework.restdocs</groupId>
      <artifactId>spring-restdocs-mockmvc</artifactId>
      <scope>test</scope>
    </dependency>
    
    <dependency>
      <groupId>io.zipkin.brave</groupId>
      <artifactId>brave-apache-http-interceptors</artifactId>
      <version>3.14.1</version>
      <scope>compile</scope>
      <optional>true</optional>
    </dependency>
    
    <dependency>
      <groupId>com.squareup.okhttp3</groupId>
      <artifactId>okhttp</artifactId>
      <version>3.4.1</version>
      <scope>test</scope>
    </dependency>
    
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>3.0.2</version>
      <exclusions>
        <!-- Exclude Groovy because of classpath issue -->
        <exclusion>
          <groupId>org.codehaus.groovy</groupId>
          <artifactId>groovy</artifactId>
        </exclusion>
      </exclusions>
      <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.codehaus.groovy</groupId>
        <artifactId>groovy-all</artifactId>
        <!-- Needs to be the same version that REST Assured depends on -->
        <version>2.4.6</version>
        <scope>test</scope>
    </dependency>
    
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>json-schema-validator</artifactId>
      <version>3.0.2</version>
      <scope>test</scope>
    </dependency>
    
    <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>1.3.2</version>
      <scope>test</scope>
    </dependency>
    
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <scope>test</scope>
    </dependency>
```


### Non-JSON API Tests
These tests cover:

* Adding a trip to a customer (which also adds a customer to a trip)
* Removing a trip from a customer (which also removes a customer from a trip)
