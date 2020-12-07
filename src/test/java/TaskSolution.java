import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @Author:Otosun Tarih :07/12/2020
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
    /** Task 1
     * create a request to https://httpstat.us/203
     * expect status 203
     * expect content type TEXT
     */
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

    /** Task 2
     * create a request to https://httpstat.us/418
     * expect status 418
     * expect content type TEXT
     * expect body to be equel to "418 I'm a teapot"
     */
    @Test
    public void task2() {
        given()
                .when()
                .get("/418")
                .then()
                .log().body()
                .statusCode(418)
                .contentType(ContentType.TEXT)
                .body(equalTo("418 I'm a teapot"));
    }

    /** Task 3
     *  create a request to https://jsonplaceholder.typicode.com/todos/2
     *  expect status 200
     *  expect content type JSON
     *  expect title in response body to be "quis ut nam facilis et officia qui"
     */
    @Test
    public void task3() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title",equalTo("quis ut nam facilis et officia qui"));
    }
}
