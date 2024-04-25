package addNewProduct;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import resuable.CreateRequestBody;

public class ValidateAddNewProduct {
    CreateRequestBody RJB;

    @BeforeClass
    public void setUp(){
        RJB = new CreateRequestBody();
    }

    @Parameters({"addProductURL","productName","expectedYear","expectedPrice"})
    @Test
    public void create_A_new_Product(String addProductURL, String productName, String expectedYear, String expectedPrice) {
        Response res = given()
                .contentType(ContentType.JSON)
                .body(RJB.addNewProductJsonbody(productName))
                .when()
                .post(addProductURL);

        int status_code = res.getStatusCode();
        String response_body = res.getBody().asString();
        String yearInResponse = res.getBody().jsonPath().getString("data.year");
        String priceInResponse = res.getBody().jsonPath().getString("data.price");
        System.out.println("The status code is: "+status_code);
        System.out.println("The response body is: "+response_body);
        Assert.assertEquals(yearInResponse,expectedYear);
        System.out.println("Year In Response: " +yearInResponse);
        Assert.assertEquals(priceInResponse,expectedPrice);
        System.out.println("Price in Response:" +priceInResponse);

    }
    @AfterClass
    public void tearDown(){

    }
}
