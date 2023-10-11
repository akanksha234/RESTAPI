package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.*;
import java.io.FileInputStream;
import java.util.Properties;

public class ApiUtils {

    private static RequestSpecification res ;
    public RequestSpecification requestSpecification() throws IOException {
        if(res == null) {
            PrintStream log = new PrintStream(new FileOutputStream("./target/logging.txt"));
            res = new RequestSpecBuilder().setBaseUri(getGobalValue("baseUri"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addQueryParam("key", "qaclick123")
                    .setContentType(ContentType.JSON)
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .build();
        }
        return  res;
    }

    public String getGobalValue(String key) throws IOException {

        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("global.properties");
        prop.load(is);
        return prop.getProperty(key);

    }

    public ResponseSpecification responseSpecification(){
        return new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    }
}
