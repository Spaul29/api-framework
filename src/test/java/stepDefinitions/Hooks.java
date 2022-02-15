package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		StepDefinationPlaceAPI sdef = new StepDefinationPlaceAPI();
		
		if(StepDefinationPlaceAPI.place_Id == null)
		{
			sdef.add_place_payload("PQRHouse", "Spanish", "XYZ Street");
			sdef.user_calls_with_http_request("AddPlaceAPI", "POST");
			sdef.verify_place_id_created_maps_to_using("PQRHouse", "getPlaceAPI");
		}
		
	}

}
