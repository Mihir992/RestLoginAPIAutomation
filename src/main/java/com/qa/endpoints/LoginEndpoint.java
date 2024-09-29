package com.qa.endpoints;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.util.Base64;
import io.restassured.path.json.JsonPath;

public class LoginEndpoint {
    /*public static String performLogin(String email, String password){
        // Create a JSON object for the login request
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        // Base64 encode the username and password
       // String credentials = Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
        Response response=given()
               // .header("Authorization", "Basic " + credentials) // Use Basic Authentication
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post(Routes.login_url);

        response.then().statusCode(200); // Ensure the response status is OK (200
        // Return the token from the response
        // Extract the token using JsonPath
        JsonPath jsonPath = response.jsonPath();
        String token = jsonPath.getString("token");
        return token;
    //}*/

    public static String performLogin(String email, String password) {
        // Base64 encode the email and password
        String credentials = Base64.getEncoder().encodeToString((email + ":" + password).getBytes());

        // Create a JSON object for the login request
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                //.header("Authorization", "Basic " + credentials) // Include Basic Authentication header
                .body(requestBody) // Include the request body with email and password
                .log().all()
                .when()
                .post(Routes.login_url);

        // Ensure the response status is OK (200)
        response.then().statusCode(200);

        // Extract the token from the response
        JsonPath jsonPath = response.jsonPath();
        String token = jsonPath.getString("token");
        return credentials;
    }
}
