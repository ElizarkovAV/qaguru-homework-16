package tests;

import models.createUserModels.CreateBodyModel;
import models.createUserModels.CreateResponseModel;
import models.loginModels.LoginBodyModel;
import models.loginModels.LoginUserNotFoundModel;
import models.userRegistrationModels.RegistrationResponseErrorModel;
import models.userRegistrationModels.RegistrationResponseModel;
import models.userRegistrationModels.RegistrationUserReqModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.CreateUserSpec.createUserResponseSpec;
import static specs.CreateUserSpec.createUserSpec;
import static specs.GetUserNotExist.getUserNotExistSpec;
import static specs.GetUserNotExist.getUserSpec;
import static specs.LoginSpec.*;
import static specs.UserRegistrationSpec.*;

@Tag("ReqResAPITests")
public class ReqresAPITests extends TestBase {

    @Test
    void getUserDoesNotExistTest() {

        step("GET request, user don't exist", () ->
        given(getUserSpec)
                .when()
                .get()
                .then()
                .spec(getUserNotExistSpec));
    }

    @Test
    void successfulCreateUserTest() {

        CreateBodyModel data = new CreateBodyModel();
        data.setName("Artem");
        data.setJob("Test Engineer");

        CreateResponseModel response = step("Make POST request create user", () ->
                given(createUserSpec)
                        .body(data)
                        .when()
                        .post()
                        .then()
                        .spec(createUserResponseSpec)
                        .extract()
                        .as(CreateResponseModel.class));
        step("Check response, name is Artem", () ->
                Assertions.assertEquals("Artem", response.getName()));
        step("Check response, job is Test Engineer", () ->
                Assertions.assertEquals("Test Engineer", response.getJob()));
    }

    @Test
    void successfulRegistrationUserTest() {

        RegistrationUserReqModel data = new RegistrationUserReqModel();
        data.setEmail("george.bluth@reqres.in");
        data.setPassword("12345");

        RegistrationResponseModel response = step("Make POST request registration user", () ->
                given(userRegisterSpec)
                        .body(data)
                        .when()
                        .post()
                        .then()
                        .spec(registerUserResponseSpec)
                        .extract()
                        .as(RegistrationResponseModel.class));
        step("Check response, id is 1", () ->
                Assertions.assertEquals("1", response.getId()));
    }

    @Test
    void unsuccessfulRegistrationUserTest() {

        RegistrationUserReqModel data = new RegistrationUserReqModel();
        data.setEmail("");
        data.setPassword("12345");

        RegistrationResponseErrorModel response = step("Make POST registration request  without email", () ->
                given(userRegisterSpec)
                        .body(data)
                        .when()
                        .post()
                        .then()
                        .spec(registerUserResponseErrorSpec)
                        .extract()
                        .as(RegistrationResponseErrorModel.class));
        step("Check error message", () ->
                Assertions.assertEquals("Missing email or username", response.getError()));
    }

    @Test
    void unsuccessfulLoginTest() {
        LoginBodyModel data = new LoginBodyModel();
        data.setUsername("Test");
        data.setEmail("123.bluth@reqres.in");
        data.setPassword("12345");

        LoginUserNotFoundModel response = step("POST login request", () ->
                        given(loginSpec)
                        .body(data)
                        .when()
                        .post()
                        .then()
                        .spec(loginUserNotFoundResponse)
                        .extract()
                        .as(LoginUserNotFoundModel.class)
                );

        step("Check response and status code for user Not Found", ()->
                Assertions.assertEquals("user not found", response.getError()));
    }
}
