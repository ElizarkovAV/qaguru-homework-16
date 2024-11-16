package tests;

import models.CreateBodyModel;
import models.CreateResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqresAPITests extends TestBase {

    @Test
    void successfulGetListOfUsersTest() {
        given()
                .when()
                .log().uri()
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
                .when()
                .log().uri()
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
                .when()
                .log().uri()
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

        CreateResponseModel response = given()
                .body(data)
                .contentType(JSON)
        .when()
                .log().uri()
                .post("/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .assertThat()
                .body("name", is("Artem"))
                .body("job", is("Test Engineer"))
                .extract()
                .as(CreateResponseModel.class);

        Assertions.assertEquals("Artem", response.getName());
    }

    @Test
    void successfulRegistrationUserTest() {
        String data = "{\"email\": \"george.bluth@reqres.in\",\"password\":\"12345\"}";

        given()
                .body(data)
                .contentType(JSON)
                .when()
                .log().uri()
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
