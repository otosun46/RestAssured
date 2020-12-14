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
import static org.hamcrest.Matchers.*;


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
        randomGenName = RandomStringUtils.randomAlphabetic(8);
        randomGenCode = RandomStringUtils.randomAlphabetic(2);

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

    String id;

    @Test
    public void createCountry() {
        Country country = new Country();
        country.setName(randomGenName);
        country.setCode(randomGenCode);
        id =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(country)
                        .when()
                        .post("/school-service/api/countries")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .body("name", equalTo(randomGenName))
                        .extract().jsonPath().getString("id")
        ;
        System.out.println(id);

    }
    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative() {
        Country country = new Country();
        country.setName(randomGenName);
        country.setCode(randomGenCode);
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(country)
                        .when()
                        .post("/school-service/api/countries")
                        .then()
                        .log().body()
                        .statusCode(400)
                        .body("message", equalTo("The Country with Name \""+randomGenName+"\" already exists."))
        ;

    }

    @Test(dependsOnMethods = "createCountry")
    public void updateCountry(){
        Country country=new Country();

        country.setId(id);
        country.setName(RandomStringUtils.randomAlphabetic(8));
        country.setCode(RandomStringUtils.randomAlphabetic(2));
        country.setCode("");
        given()
                .cookies(cookies)
                .body(country)
                .contentType(ContentType.JSON)
                .when()
                .put("/school-service/api/countries")
                .then()
                .statusCode(200)
                .body("name",equalTo(country.getName()))
                .body("code",equalTo(country.getCode()))
                ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteById(){

        given()
                .cookies(cookies)
                .pathParam("id",id)
                .when()
                .delete("/school-service/api/countries/{id}")
                .then()
                .statusCode(200)
                .body(equalTo(""))
        ;
    }
    @Test(dependsOnMethods = "deleteById")
    public void deleteCountryByIdNegativTest() {

        given()
                .cookies(cookies)
                .pathParam("countryId", id)
                .when()
                .delete("/school-service/api/countries/{countryId}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Country not found"))

        ;
    }

}
