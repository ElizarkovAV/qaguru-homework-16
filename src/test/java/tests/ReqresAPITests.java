package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.CreateBodyModel;
import models.CreateResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqresAPITests extends TestBase {

    @Test
    void successfulGetListOfUsersTest() {
        given()
                .log().uri()
                .log().body()
                .log().headers()
        .when()
                .get("/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("total", is(12));
    }

    @Test
    void successfulGetUserTest() {
        given()
                .log().uri()
                .log().body()
                .log().headers()
        .when()
                .get("/users/1")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("data.first_name", is("George"))
                .body("data.last_name", is("Bluth"));
    }

    @Test
    void getUserDoesNotExistTest() {
        given()
                .log().uri()
                .log().body()
                .log().headers()
        .when()
                .get("/users/100")
        .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void successfulCreateUserTest() {

        CreateBodyModel data = new CreateBodyModel();
        data.setName("Artem");
        data.setJob("Test Engineer");

        CreateResponseModel response = step("Make request", () ->
                      given()
                            .filter(withCustomTemplates())
                            .body(data)
                            .contentType(JSON)
                            .log().uri()
                            .log().body()
                            .log().headers()
                            .when()
                            .post("/users")
                            .then()
                            .log().status()
                            .log().body()
                            .statusCode(201)
                            .assertThat()
                            .body("name", is("Artem"))
                            .body("job", is("Test Engineer"))
                            .extract()
                            .as(CreateResponseModel.class));
        step("Check response" , () ->
            Assertions.assertEquals("Artem", response.getName()));
    }

    @Test
    void successfulRegistrationUserTest() {
        String data = "{\"email\": \"george.bluth@reqres.in\",\"password\":\"12345\"}";

        given()
                .body(data)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
        .when()
                .post("/register")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("id", is(1))
                .body("token", is("QpwL5tke4Pnpja7X1"));
    }
}
