package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.ExtractableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import pojo.*;
import utils.APIResources;
import utils.HTTPMethod;
import utils.TestDataBuild;
import utils.ApiUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;


public class StepDefinition extends ApiUtils {
    AddPlaceResponse addPlaceResponse;
    TestDataBuild data = new TestDataBuild();
    ExtractableResponse response;
    RequestSpecification s;
    public static String place_id;
    GetPlaceResponse getPlaceResponse;
    DeletePlaceResponse deletePlaceresponse;

    @Given("Add Place Payload {string} {string} {string}")
    public void add_place_payload(String name, String language, String address) throws IOException {
        AddPlaceRequest addPlace = data.addPlacePayload(name, language, address);
        s = given().spec(requestSpecification()).body(addPlace);
    }

    /**
     * @param api the API which is being called
     * @author Akanksha Singh
     */
    @When("I send a {string} http request to {string} endpoint")
    public void user_calls_with_http_request(String httpMethod, String api) throws IOException {
        APIResources resource = APIResources.valueOf(api);
        HTTPMethod method = HTTPMethod.valueOf(httpMethod.toUpperCase());

        switch (method) {
            case POST:
                response = s.when().post(resource.getResourse())
                        .then().spec(responseSpecification()).extract();
                addPlaceResponse = response.as(AddPlaceResponse.class);
                place_id = addPlaceResponse.getPlace_id();
                break;
            case GET:
                s = given().spec(requestSpecification()).
                        queryParam("place_id", place_id);
                response = s.when().get(resource.getResourse()).then().extract();
                getPlaceResponse = response.as(GetPlaceResponse.class);
                break;
            case DELETE:
                response = s.when().delete(resource.getResourse()).then().extract();
                deletePlaceresponse = response.as(DeletePlaceResponse.class);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP Method: " + httpMethod);
        }
    }

    /**
     * @param expectedStatusCode int
     * @author Akanksha Singh
     */
    @Then("I validate that API call is a success with status code {int}")
    public void validate_that_api_call_is_a_success_with_status_code(Integer expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode.intValue(), response.statusCode());

    }

    @Then("{string} in the response body is {string}")
    public void in_the_response_body_is(String field, String value) {
        if (field.equals("status")) {
            Assert.assertEquals(value, addPlaceResponse.getStatus());
        } else if (field.equals("scope")) {
            Assert.assertEquals(value, addPlaceResponse.getScope());
        }

    }

    @And("I validate place_id maps to the same {string} using {string}")
    public void iValidatePlace_idMapsToTheSameUsing(String name, String api) throws IOException {
        user_calls_with_http_request("get", api);

        Assert.assertEquals(name, getPlaceResponse.getName());
    }

    @Given("DeletePlace payload")
    public void deleteplacePayload() throws IOException {
        s = given().spec(requestSpecification())
                .body(data.deleteplacePayload(place_id));

    }

    @When("I send a {string} Request to {string} endpoint")
    public void iSendARequestToEndpoint(String httpMethod, String api) throws IOException {
        user_calls_with_http_request(httpMethod, api);
        
    }

    @Then("I validate that the status code is {int}")
    public void iValidateThatTheStatusCodeIs(Integer statusCode) {
        validate_that_api_call_is_a_success_with_status_code(statusCode);
    }

    @And("I validate that the status in the response body is {string}")
    public void iValidateThatTheInTheResponseBodyIs(String value) {
        Assert.assertEquals(value, deletePlaceresponse.getStatus());
    }

}


