/**
 * @Author:Otosun Tarih :10/12/2020
 */
package Basqar;

import Basqar.Model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class CountryTest {
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;
    Cookies cookies;
    private String randomGenName;
    private String randomGenCode;

    @BeforeClass
    public void login() {

        //{"username": "daulet2030@gmail.com", "password": "TechnoStudy123@", "rememberMe": true}

        baseURI = "https://test.basqar.techno.study";
        randomGenName=RandomStringUtils.randomAlphabetic(8);
        randomGenCode=RandomStringUtils.randomAlphabetic(2);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "daulet2030@gmail.com");
        credentials.put("password", "TechnoStudy123@");
        credentials.put("rememberMe", "true");
        cookies =
        given()
                .body(credentials)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response().getDetailedCookies()
        ;
        System.out.println(cookies);
    }
    @Test
    public void createCountry(){
        Country country=new Country();
        country.setName(randomGenName);
        country.setCode(randomGenCode);

        given()
                .body(country)
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when()
                .then()

                ;

    }


}
