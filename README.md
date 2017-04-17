# Mystic Dream: Customer API


## Overview
The support team at Mystic Dream is streamlining their customer logistics. With a spike in new cutomers, it is more difficult to manage and track all the steps involved in scheduling customer trips. An interface has been built.

The goal of this project is to build two APIs that:
 
* Manage customer information
* Manage and track steps in the planning process

## Code template
This code borrows heavily from a [Spring REST tutorial](http://spring.io/guides/tutorials/bookmarks/) as well as the [Katharsis Spring example](https://github.com/katharsis-project/katharsis-framework/tree/master/katharsis-examples/spring-boot-simple-example).

## Dependencies
The code uses: 

* Spring Boot
* H2
* Maven
* Katharsis

## Running with Maven
```
$ mvn clean spring-boot:run
```

## Calls
I found [Postman](https://www.getpostman.com/) invaluable for testing the calls. It is available as a Chrome extension or Mac/Windows/Linux application.

```
# Get all customers
GET http://localhost:8080/api/customer/
```

```
# Get customer id 1
GET http://localhost:8080/api/customer/1
```

```
# Create new customer
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

```
# Create new trip
POST http://localhost:8080/api/trip

# body; type: json
{
  "data": {
    "type": "trip",
    "attributes": {
      "description": "Mars adventure"
    }
  }
}
```

``` 
# Create activity log entry
POST http://localhost:8080/api/activityLog

# body; type: json
{
  "data": {
    "type": "activityLog",
    "attributes": {
        "description": "Customer called wanting a trip",
        "createdById": "1",
        "activeTrip": "true"
    },
    "relationships": {
    	"customer": {
        	"data": {
        		"id": "1",
            	"type": "customer"
     		}
		}
  	}
	}
}
```

# Create activity log entry with trip
POST http://localhost:8080/api/activityLog

# body; type: json
{
  "data": {
    "type": "activityLog",
    "attributes": {
        "description": "Customer booked a trip to Mars",
        "createdById": "1",
        "activeTrip": "true"
    },
    "relationships": {
    	"customer": {
        	"data": {
        		"id": "1",
            	"type": "customer"
     		}
		},
		"trip": {
          "data": {
            "id": "5",
            "type": "trip"
        	}
        }
  	}
	}
}
```

```
#  Update relationship between trip and customer
PATCH http://localhost:8080/api/trip/5/relationships/customer

# body; type: json
{
  "data": {
    "type": "customer",
    "id": 1
  }
}
```


## Implementation