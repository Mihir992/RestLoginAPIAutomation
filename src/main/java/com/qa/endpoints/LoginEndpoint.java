package com.qa.endpoints;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.util.Base64;
import io.restassured.path.json.JsonPath;

public class LoginEndpoint {
    public static String performLogin(String username, String password){
        // Base64 encode the username and password
        String credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Response response=given()
                .header("Authorization", "Basic " + credentials) // Use Basic Authentication
                .header("Content-Type", "application/json")
                .post(Routes.login_url);

        response.then().statusCode(200); // Ensure the response status is OK (200
        // Return the token from the response
        // Extract the token using JsonPath
        JsonPath jsonPath = response.jsonPath();
        String token = jsonPath.getString("token");
        return token;
    }
}
