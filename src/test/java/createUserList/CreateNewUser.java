package createUserList;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import resuable.CreateRequestBody;

public class CreateNewUser {

    CreateRequestBody RJB;

    @BeforeClass
    public void setUp(){
    RJB = new CreateRequestBody();
    }

    @Parameters({"userCreationURL","SSNno","userName"})
    @Test
    public void create_A_new_User(String userCreationURL, String SSNno, String userName) {
        Response res = given()
            .contentType(ContentType.JSON)
            .body(RJB.CreateUserJsonBody(userName,SSNno))
            .when()
            .post(userCreationURL);

        int status_code = res.getStatusCode();
        String response_body = res.getBody().asString();
        String userid = res.getBody().jsonPath().getString("id");
        System.out.println("The status code is: "+status_code);
        System.out.println("The response body is: "+response_body);
        System.out.println("The User Id is: "+userid);

    }
    @Parameters({"CelsiusToFahrenheitURL","celsius","fahrenheit"})
    @Test
    public void convert_CelsiusToFahrenheit(String CelsiusToFahrenheitURL, String celsius, String fahrenheit) {
        Response res = given()
                .contentType(ContentType.XML)
                .header("Content-Type","text/xml; charset=utf-8")
                .body(RJB.CreateCelsiusToFahrenheitXMLbody(celsius))
                .when()
                .post(CelsiusToFahrenheitURL);

        int status_code = res.getStatusCode();
        String response_body = res.getBody().asString();
        //String userid = res.getBody().jsonPath().getString("id");
        System.out.println("The status code is: "+status_code);
        System.out.println("The response body is: "+response_body);
        //System.out.println("The User Id is: "+userid);

    }

    @AfterClass
    public void tearDown(){

    }

}
