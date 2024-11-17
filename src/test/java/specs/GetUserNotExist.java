package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class GetUserNotExist {

    public static RequestSpecification getUserSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers()
            .basePath("api/users/531");

    public static ResponseSpecification getUserNotExistSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(STATUS)
            .build();


}
