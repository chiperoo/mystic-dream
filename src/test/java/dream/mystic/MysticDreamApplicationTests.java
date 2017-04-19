package dream.mystic;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import dream.mystic.domain.Customer;

import dream.mystic.repository.jsonapi.CustomerResourceRepository;
import dream.mystic.repository.jsonapi.CustomerResourceRepository.CustomerList;
import dream.mystic.repository.jsonapi.CustomerResourceRepository.CustomerListLinks;
import dream.mystic.repository.jsonapi.CustomerResourceRepository.CustomerListMeta;
import io.katharsis.queryspec.QuerySpec;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class MysticDreamApplicationTests extends BaseTest {

	@Before
	public void setup() {
	}

	@Test
	public void testKatharsisClient() {
		
		CustomerResourceRepository custRepo = 
				client.getRepositoryForInterface(CustomerResourceRepository.class);
		
		QuerySpec querySpec = new QuerySpec(Customer.class);
		querySpec.setLimit(10L);
				
		CustomerList logList = custRepo.findAll(querySpec);
		
		Assert.assertNotEquals(0, logList.size());

		// test meta access
		CustomerListMeta meta = logList.getMeta();
		Assert.assertEquals(4L, meta.getTotalResourceCount().longValue());

		// test pagination links access
		CustomerListLinks links = logList.getLinks();
		Assert.assertNotNull(links.getFirst());
	}
	
	@Test
	public void testFindOne() {
		testFindOne("/api/customer/1");
		testFindOne("/api/user/1");
		testFindOne("/api/trip/3");
		testFindOne("/api/activityLog/3");
	}

	@Test
	public void testFindOne_NotFound() {
		testFindOne_NotFound("/api/customer/0");
		testFindOne_NotFound("/api/user/0");
		testFindOne_NotFound("/api/trip/0");
		testFindOne_NotFound("/api/activityLog/0");
	}

	@Test
	public void testFindMany() {
		testFindMany("/api/customer");
		testFindMany("/api/user");
		testFindMany("/api/trip");
		testFindMany("/api/activityLog");
	}
	
	@Test
	public void testDelete() {
		testDelete("/api/customer/1");
		testDelete("/api/user/1");
		testDelete("/api/trip/1");
		testDelete("/api/activityLog/1");
	}

	@Test
	public void testCreateCustomer() {
		Map<String, Object> attributeMap = new ImmutableMap.Builder<String, Object>().put("name", "oswald")
				.put("emailAddress", "oswald@fake.email").build();

		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "customer", "attributes", attributeMap));

		ValidatableResponse response = RestAssured.given().contentType("application/json").body(dataMap).when().post("/api/customer")
				.then().statusCode(CREATED.value());
		response.assertThat().body(matchesJsonSchema(jsonApiSchema));
	}
	
	@Test
	public void testUpdateCustomer() {
		Map<String, Object> attributeMap = new ImmutableMap.Builder<String, Object>().put("name", "batman")
				.put("emailAddress", "batman@fake.email").build();

		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "customer", "id", 1, "attributes", attributeMap));

		RestAssured.given().contentType("application/json").body(dataMap).when().patch("/api/customer/1").then()
				.statusCode(OK.value());
	}

	@Test
	public void testCreateTrip() {
		Map<String, Object> attributeMap = new ImmutableMap.Builder<String, Object>().put("description", "Metropolis")
				.build();

		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "trip", "attributes", attributeMap));

		ValidatableResponse response = RestAssured.given().contentType("application/json").body(dataMap).when().post("/api/trip")
				.then().statusCode(CREATED.value());
		response.assertThat().body(matchesJsonSchema(jsonApiSchema));
	}
	
	@Test
	public void testUpdateTrip() {
		Map<String, Object> attributeMap = new ImmutableMap.Builder<String, Object>().put("description", "Arkham")
				.build();

		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "trip", "id", 1, "attributes", attributeMap));

		RestAssured.given().contentType("application/json").body(dataMap).when().patch("/api/trip/1").then()
				.statusCode(OK.value());
	}
	
	@Test
	public void testCreateUser() {
		Map<String, Object> attributeMap = new ImmutableMap.Builder<String, Object>().put("name", "gorilla")
				.put("emailAddress", "gorilla@fake.email").build();

		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "user", "attributes", attributeMap));

		ValidatableResponse response = RestAssured.given().contentType("application/json").body(dataMap).when().post("/api/user")
				.then().statusCode(CREATED.value());
		response.assertThat().body(matchesJsonSchema(jsonApiSchema));
	}
	
	@Test
	public void testUpdateUser() {
		Map<String, Object> attributeMap = new ImmutableMap.Builder<String, Object>().put("name", "admin-cat")
				.put("emailAddress", "admin.cat@fake.email").build();

		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "user", "id", 1, "attributes", attributeMap));

		RestAssured.given().contentType("application/json").body(dataMap).when().patch("/api/user/1").then()
				.statusCode(OK.value());
	}
	
	@Test
	public void testCreateActivityLogEntry() {
		Map<String, Object> attributeMap = new ImmutableMap.Builder<String, Object>().put("createdById", "1")
				.put("description", "test activity log entry").build();
		
		Map<String, Object> userMap = new ImmutableMap.Builder<String, Object>().put("id","1").put("type","user").build();
		Map<String, Object> customerMap = new ImmutableMap.Builder<String, Object>().put("id","2").put("type","customer").build();
		
		Map user = ImmutableMap.of("data", userMap);
		Map customer =  ImmutableMap.of("data", customerMap);
		
		Map<String, Object> relationshipMap = new ImmutableMap.Builder<String, Object>().put("user", user)
				.put("customer", customer).build();
		

		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "activityLog", "attributes", attributeMap, 
				"relationships", relationshipMap));

		ValidatableResponse response = RestAssured.given().contentType("application/json").body(dataMap).when().post("/api/activityLog")
				.then().statusCode(CREATED.value());
		response.assertThat().body(matchesJsonSchema(jsonApiSchema));
	}
	
	@Test
	public void updateActivityLogEntry() {
		Map<String,Object> attributeMap = new ImmutableMap.Builder<String,Object>().put("important", "true").build();
		
		Map dataMap = ImmutableMap.of("data", ImmutableMap.of("type", "activityLog", "attributes", attributeMap));
		
		RestAssured.given().contentType("application/json").body(dataMap).when().patch("/api/activityLog/2").then()
				.statusCode(OK.value());
	}
	
}
