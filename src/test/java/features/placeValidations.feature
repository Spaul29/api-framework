
Feature: Validating Place API

	@AddPlace
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"
    
  Examples:
  	| name			| language 	| address  				|
  	| ABCHouse	| English 	| San Fransisco  	|
 # 	| LMNHouse	| French	 	| Las Vegas			 	|

 @DeletePlace
 Scenario: Verify if place is successfully deleted using deletePlaceAPI
 	 Given Delete Place Payload
 	 When user calls "deletePlaceAPI" with "Post" http request
   Then the API call got success with status code 200
   And "status" in response body is "OK"
    