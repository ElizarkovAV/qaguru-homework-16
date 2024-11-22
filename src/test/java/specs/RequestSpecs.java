package specs;

import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class RequestSpecs {

    public static RequestSpecification getUserRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();

    public static RequestSpecification postCreateUserSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();

    public static RequestSpecification postUserRegisterSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();

    public static RequestSpecification postLoginSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();
}
