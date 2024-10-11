package com.qa.endpoints;

import com.qa.payload.UserPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserEndpoint {

    public static String createUser(UserPayload payload)
    {
        Response response=given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post(Routes.postUser_url);
        // Extract user ID from response
        return response.jsonPath().getString("id");
    }

    public static Response updateUser(int Id,UserPayload payload)
    {
        Response response=given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("Id", Id)
                .body(payload)
                .when()
                .put(Routes.putUser_url + "/{Id}");
        return response;
    }
}
