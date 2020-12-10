/**
 * @Author:Otosun Tarih :10/12/2020
 */
package Basqar;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class CountryTest {
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;
    Cookies cookes;

    @BeforeClass
    public void login() {

        //{"username": "daulet2030@gmail.com", "password": "TechnoStudy123@", "rememberMe": true}

        baseURI = "https://test.basqar.techno.study";

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "daulet2030@gmail.com");
        credentials.put("password", "TechnoStudy123@");
        credentials.put("rememberMe", "true");
        cookes=
        given()
                .body(credentials)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response().getDetailedCookies()
        ;
        System.out.println(cookes);
    }
    @Test
    public void createCountry(){

    }
}
