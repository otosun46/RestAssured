import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

/**
 * @Author:Otosun Tarih :07/12/2020
 */
/** Task 1
 * create a request to https://httpstat.us/203
 * expect status 203
 * expect content type TEXT
 */


public class TaskSolution {
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;

    @BeforeClass
    public void setup() {
        baseURI = "https://httpstat.us";
        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void task1() {
                given()
                        .when()
                        .get("/203")
                        .then()
                         .log().body()
                        .statusCode(203)
                        .contentType(ContentType.TEXT);
    }
}
