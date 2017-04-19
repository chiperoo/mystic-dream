package dream.mystic;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.katharsis.client.KatharsisClient;
import io.katharsis.jpa.JpaModule;
import io.katharsis.validation.ValidationModule;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysticDreamApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {
	
	@LocalServerPort
	private int port;
	
	protected String jsonApiSchema;

	protected KatharsisClient client;	

	@Before
	public final void before() {
		RestAssured.port = port;
		loadJsonApiSchema();

		client = new KatharsisClient("http://localhost:" + port + "/api");
		client.addModule(ValidationModule.newInstance());
		client.addModule(JpaModule.newClientModule("io.katharsis.example.springboot.simple.domain.jpa"));
	}
	
	private void loadJsonApiSchema() {
		try {
			jsonApiSchema = loadFile("json-api-schema.json");
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String loadFile(String filename) throws Exception {
		InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream(filename);
		return IOUtils.toString(inputStream);
	}

	protected void testFindOne(String url) {
		ValidatableResponse response = RestAssured.given().contentType("application/json").when().get(url).then()
				.statusCode(OK.value());
		response.assertThat().body(matchesJsonSchema(jsonApiSchema));
	}

	protected void testFindOne_NotFound(String url) {
		RestAssured.given().contentType("application/json").when().get(url).then().statusCode(NOT_FOUND.value());
	}

	protected void testFindMany(String url) {
		ValidatableResponse response = RestAssured.given().contentType("application/json").when().get(url).then()
				.statusCode(OK.value());
		response.assertThat().body(matchesJsonSchema(jsonApiSchema));
	}

	protected void testDelete(String url) {
		//RestAssured.given().contentType("application/json").when().delete(url).then().statusCode(NO_CONTENT.value());
		RestAssured.given().contentType("application/json").when().delete(url).then().statusCode(FORBIDDEN.value());
	}

}
