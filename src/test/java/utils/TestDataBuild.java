package utils;

import pojo.AddPlaceRequest;
import pojo.DeletePlaceRequest;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlaceRequest addPlacePayload(String name, String language, String address) {
        String phoneNumber = "(+91) 983 893 3937";
        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        //Serialise - object to json
        AddPlaceRequest addplace = new AddPlaceRequest();
        addplace.setAccuracy(50);
        addplace.setAddress(address);
        addplace.setLanguage(language);
        addplace.setName(name);
        addplace.setWebsite("http://google.com");
        addplace.setTypes(types);
        addplace.setLocation(location);
        addplace.setPhoneNumber(phoneNumber);

        return addplace;
    }


    public DeletePlaceRequest deleteplacePayload(String place_id) {
        DeletePlaceRequest deletePlaceRequest = new DeletePlaceRequest();
        deletePlaceRequest.setPlace_id(place_id);
        return deletePlaceRequest;
    }
}