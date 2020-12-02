import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class ZippoTest {
    //initial
    @Test
    public void test() {
        given()
                .when()
                .then();
    }

    @Test
    public void statusCodeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void contentTypeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().all()
                .contentType(ContentType.JSON)
        ;
    }

    @Test
    public void logRequestAndResponseDetails() {
        given().
                log().all().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                log().body();
    }

    @Test
    public void bodyJsonPathTest() {
        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                log().body()
                .body("country", equalTo("United States"))
                .statusCode(200);
    }

    @Test
    public void bodyJsonPathTest2() {
        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyJsonPathTest3() {
        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyArraySizeTest() {
        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)
        ;
    }

    @Test
    public void CombiningTest() {
        given()
                .when()
                .get("http://zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("country", equalTo("United States"))
                .body("places[0].state", equalTo("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .body("places", hasSize(1))
                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest() {
        String country = "us";
        String zipCode = "90210";
        given()
                .pathParam("country", country)
                .pathParam("zipCode", zipCode)
                .log().uri()
                .when()
                .get("http://zippopotam.us" + "/{country}/{zipCode}")
                .then()
                .log().body()
                .body("places", hasSize(1))
        ;
    }

    @Test
    public void queryParamTest() {
        int page = 10;

        given()
                .param("page", page)
                .log().uri()
                .when()
                .get("http://gorest.co.in/public-api/users")
                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(page))
        ;
    }

    @Test
    public void queryParamTestCoklu() {

        for (int page = 1; page < 10; page++) {
            given()
                    .param("page", page)
                    .log().uri()
                    .when()
                    .get("http://gorest.co.in/public-api/users")
                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(page))
            ;
        }
    }
}
