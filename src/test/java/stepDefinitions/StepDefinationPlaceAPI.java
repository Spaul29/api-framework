package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefinationPlaceAPI extends Utils {
	
	RequestSpecification rspec;
	ResponseSpecification responseSpecification;
	Response response;
	static String place_Id;
	
	TestDataBuild data = new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException {
		
		rspec = given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
		
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String methodType) {
		
		APIResources resourcePath = APIResources.valueOf(resource);
	    
		//responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(methodType.equalsIgnoreCase("POST"))
			response = rspec.when().post(resourcePath.getResource());
		else if(methodType.equalsIgnoreCase("GET"))
			response = rspec.when().get(resourcePath.getResource());
			
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	   
	   assertEquals(200,response.getStatusCode());
	}

	@And("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
	    
	    assertEquals(getJsonPath(response, key),value);
	    
	}
	
	@And("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	   
		place_Id = getJsonPath(response, "place_id");
		rspec = given().spec(requestSpecification()).queryParam("place_id", place_Id);
		
		user_calls_with_http_request(resource,"GET");
		assertEquals(getJsonPath(response, "name"),expectedName);
		
		
	}

	
	@Given("Delete Place Payload")
	public void delete_place_payload() throws IOException {
		
		rspec = given().spec(requestSpecification()).body(data.deletePlacePayload(place_Id));
		
	   
	}
}
