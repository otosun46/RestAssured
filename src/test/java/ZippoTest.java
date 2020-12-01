import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ZippoTest {
//initial
    @Test
    public void test()
    {
        given()
                .when()
                .then();
    }

    @Test
    public void statusCodeTest(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
        .log().all()
                ;
    }
}
