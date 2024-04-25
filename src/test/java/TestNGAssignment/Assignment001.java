package TestNGAssignment;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.get;

public class Assignment001 {
    @BeforeClass
    public void setup()
    {

    }
    @Test
    public void productResponse_validation()
    {
        String requestURL = "https://dummyjson.com/products";
        Response response = get(requestURL);
        //validate the status code
        String status_code = String.valueOf(response.statusCode());
        Assert.assertEquals(status_code,"200");
        System.out.println("Status Code in the response is: " +status_code);
        //validate the title is "iPhone 9"
        String title = response.getBody().jsonPath().getString("products[0].title");
        Assert.assertEquals(title,"iPhone 9");
        System.out.println("title in the response is: " +title);
        //validate the "price": 549
        String price = response.getBody().jsonPath().getString("products[0].price");
        Assert.assertEquals(price,"549");
        System.out.println("price in the response is: " +price);
        //validate the "brand": "Apple"
        String brand = response.getBody().jsonPath().getString("products[0].brand");
        Assert.assertEquals(brand,"Apple");
        System.out.println("brand in the response is: " +brand);
        //validate total images count is 5
        int images_count = response.getBody().jsonPath().getList("products[0].images").size();
        Assert.assertEquals(images_count,5);
        System.out.println("total images count in the response is: " +images_count);
        //Validate that all image has .jpg file extension
        for (int i=0;i<images_count;i++){
            String imageURL =response.getBody().jsonPath().getString("products[0].images["+i+"]");
            if(imageURL.contains(".jpg")){
                System.out.println("Image "+i+" has .jpg file extension");
            }
            else {
                System.out.println("Image "+i+" not has .jpg file extension");
            }
        }

    }

    @AfterClass
    public void tearDown()
    {

    }
}
/* Output
C:\Users\sunilk\IdeaProjects\TestNG_RestAssurd>mvn clean test -Dsurefire.suiteXmlFiles=AssignmentXml/assignment001.xml

 [INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Status Code in the response is: 200
title in the response is: iPhone 9
price in the response is: 549
brand in the response is: Apple
total images count in the response is: 5
Image 0 has .jpg file extension
Image 1 has .jpg file extension
Image 2 has .jpg file extension
Image 3 has .jpg file extension
Image 4 has .jpg file extension
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.838 s - in TestSuite
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
 */

