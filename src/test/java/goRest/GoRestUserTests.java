/**
 * @Author:Otosun Tarih :08/12/2020
 */
package goRest;

import goRest.Model.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUserTests {
    int userId;

    @Test
    public void getUsers() {
        List<User> userList =
                given()
                        .when()
                        .get("https://gorest.co.in/public-api/users")
                        .then()
                        // .log().body()
                        .statusCode(200) //donen durumun kontrolunu yaptik
                        .contentType(ContentType.JSON)//veri tipini kontrol ettik
                        .body("code", equalTo(200))//donen body nin ilk bolumundeki code n degeri kontrol edildi
                        .body("data", not(empty()))//datanin bos olup olmadigi kontrol edildi
                        .extract().jsonPath().getList("data", User.class);
        for (User us : userList) {
            System.out.println(us.toString());
        }
    }

    @Test
    public void createUser() {
        userId =
                given()
                        .header("Authorization", "Bearer 04610a7e22f479adbcf2d70d5f61babda270b70b86318c203a6e7ac1e7ce1ee3")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\"technoOr \", \"gender\":\"Male\", \"email\":\"" + getRandomEmail() + "\", \"status\":\"Active\"}")
                        .when()
                        .post("https://gorest.co.in/public-api/users")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("code", equalTo(201))
                        .extract().jsonPath().getInt("data.id")
        ;
        System.out.println(userId);


    }

    private String getRandomEmail() {
        return RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
    }

    @Test(dependsOnMethods = "createUser")
    public void getUserById() {
        given()
                .pathParam("userId", userId)
                .when()
                .get("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("data.id", equalTo(userId))
        ;
    }

    String newName = "Hasan Sahan";
    @Test(dependsOnMethods = "createUser")
    public void updateUserById() {

        given()
                .header("Authorization", "Bearer 04610a7e22f479adbcf2d70d5f61babda270b70b86318c203a6e7ac1e7ce1ee3")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+ newName + "\"}")
                .pathParam("userId", userId)
                .when()
                .put("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("data.name", equalTo(newName))
        ;
    }

    @Test (dependsOnMethods = "createUser",priority = 1)
    public void deleteUserById(){
        given()
                .header("Authorization", "Bearer 04610a7e22f479adbcf2d70d5f61babda270b70b86318c203a6e7ac1e7ce1ee3")
                .pathParam("userId", userId)
                .when()
                .delete("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code",equalTo(204))
        ;
    }

    @Test (dependsOnMethods = "createUser",priority = 1)
    public void deleteUserByIdNegative(){
        given()
                .header("Authorization", "Bearer 04610a7e22f479adbcf2d70d5f61babda270b70b86318c203a6e7ac1e7ce1ee3")
                .pathParam("userId", userId)
                .when()
                .delete("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code",equalTo(404))
        ;
    }

    @Test
    public void responsSampla(){
        Response response=
        given()

                .when()
                .get("https://gorest.co.in/public-api/users")
                .then()
                //.log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response()
                ;
        User user2=response.jsonPath().getObject("data[1]",User.class);
        List<User> userList=response.jsonPath().getList("data",User.class);
        int total= response.jsonPath().getInt("meta.pagination.total");
        int code= response.jsonPath().getInt("code");
        System.out.println(user2);
        System.out.println(userList.size());
        System.out.println(total);
        System.out.println(code);
    }
}
