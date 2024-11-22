package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class ResponseSpecs {

    public static ResponseSpecification getUserResponse404Spec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(STATUS)
            .build();

    public static ResponseSpecification createUserResponse201Spec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification registerUserResponse200Spec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification registerUserResponse400Spec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification loginUserResponse400Spec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(BODY)
            .build();

}
