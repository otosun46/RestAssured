/**
 * @Author:Otosun Tarih :08/12/2020
 */
package goRest;
import goRest.Model.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUserTests {
    @Test
    public void getUsers(){
        List<User>userList=
        given()
                .when()
                .get("https://gorest.co.in/public-api/users")
                .then()
               // .log().body()
                .statusCode(200) //donen durumun kontrolunu yaptik
                .contentType(ContentType.JSON)//veri tipini kontrol ettik
                .body("code", equalTo(200))//donen body nin ilk bolumundeki code n degeri kontrol edildi
                .body("data",not(empty()))//datanin bos olup olmadigi kontrol edildi
                .extract().jsonPath().getList("data", User.class)
                ;
        for (User us: userList) {
            System.out.println(us.toString());
        }
    }

}
