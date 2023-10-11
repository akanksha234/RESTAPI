package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void  beforeScenario() throws IOException {
        StepDefinition st =  new StepDefinition();

        if(StepDefinition.place_id==null){
            st.add_place_payload("Goyal Footprints", "Hindi", "Sri Balaji Krupa Layout");
            st.user_calls_with_http_request("post", "AddPlaceAPI");
        }

    }
}
