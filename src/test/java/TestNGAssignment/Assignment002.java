package TestNGAssignment;

import com.beust.ah.A;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import resuable.CreateRequestBody;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Assignment002 {

    CreateRequestBody RJB;
    String idFromResponse = null;

    @BeforeClass
    public void setUp(){
        RJB = new CreateRequestBody();
    }

    @Parameters({"requestURL","name","year","DOB","Address","Salary"})
    @Test(priority=0)
    public void create_new_BankEmployee(String requestURL, String name, String year, String dob, String address, String salary) {
        Response res = given()
                .contentType(ContentType.JSON)
                .body(RJB.createBankEmployeeRequestBody(name,year,dob,address,salary))
                .when()
                .post(requestURL);

        //validate the status code
        int status_code = res.getStatusCode();
        Assert.assertEquals(status_code,200);
        System.out.println("The status code after post call is: "+status_code);
        //validate the id key and get its value from response
        String response_body = res.getBody().asString();
        if(response_body.contains("id")){
            idFromResponse = res.getBody().jsonPath().getString("id");
            System.out.println("The Id in the response is: "+idFromResponse);
        }

    }
    @Parameters({"requestURL","name","year","DOB","Address","Salary"})
    @Test(priority=1)
    public void get_BankEmployeeDetails_to_Validate (String requestURL, String name, String year, String dob, String address, String salary) {

        String apiPathOfCreatedBankEmployee = requestURL+"?id="+idFromResponse;
        Response response = get(apiPathOfCreatedBankEmployee);
        //System.out.println("API path: "+apiPathOfCreatedBankEmployee);
       // System.out.println("Response: "+response.getBody().asString());

        //validate the status code
        String status_code = String.valueOf(response.statusCode()); // converting response.statusCode from int to String
        Assert.assertEquals(status_code,"200");
        System.out.println("The status code after get call is: "+status_code);
        //validate name
        String nameInResponse = response.getBody().jsonPath().getString("name[0]");
        Assert.assertEquals(nameInResponse,name);
        System.out.println("Value of name in response is: "+nameInResponse);
        //validate year
        String yearInResponse = response.getBody().jsonPath().getString("data[0].year");
        Assert.assertEquals(yearInResponse,year);
        System.out.println("Value of year in response is: "+yearInResponse);
        //validate dob
        String dobInResponse = response.getBody().jsonPath().getString("data[0].DOB");
        Assert.assertEquals(dobInResponse,dob);
        System.out.println("Value of dob in response is: "+dobInResponse);
        //validate Address
        String addressInResponse = response.getBody().jsonPath().getString("data[0].Address");
        Assert.assertEquals(addressInResponse,address);
        System.out.println("Value of Address in response is: "+addressInResponse);
        //validate Salary
        String salaryInResponse = response.getBody().jsonPath().getString("data[0].Salary");
        Assert.assertEquals(salaryInResponse,salary);
        System.out.println("Value of Salary in response is: "+salaryInResponse);

    }

    @Parameters({"requestURL","name","year","DOB","UpdatedAddress","Salary"})
    @Test(priority=2)
    public void putCallToUpdateAddressOfBankEmployeeDetails_Validation(String requestURL, String name, String year, String dob, String UpdatedAddress, String salary){

        String put_apiPathOfCreatedBankEmployee = requestURL+"/"+idFromResponse;
        Response res = given()
                .contentType(ContentType.JSON)
                .body(RJB.createBankEmployeeRequestBody(name,year,dob,UpdatedAddress,salary))
                .when()
                .put(put_apiPathOfCreatedBankEmployee);

        //validate the status code
        String status_code = String.valueOf(res.statusCode()); // converting response.statusCode from int to String
        Assert.assertEquals(status_code,"200");
        System.out.println("The status code after put is: "+status_code);
        //validate Address
        String addressInResponse = res.getBody().jsonPath().getString("data.Address");
        //System.out.println("Response after Put Call: "+res.asString());
        Assert.assertEquals(addressInResponse,UpdatedAddress);
        System.out.println("Value of UpdatedAddress in response is: "+addressInResponse);

        //Again a GET call to validate new Address

        String apiPathOfCreatedBankEmployee = requestURL+"?id="+idFromResponse;
        Response response = get(apiPathOfCreatedBankEmployee);

        //validate the status code
        String newStatus_code = String.valueOf(response.statusCode()); // converting response.statusCode from int to String
        Assert.assertEquals(newStatus_code,"200");
        System.out.println("The status code after get call is: "+newStatus_code);

        //validate Address
        String newAddressInResponse = response.getBody().jsonPath().getString("data[0].Address");
        Assert.assertEquals(addressInResponse,UpdatedAddress);
        System.out.println("Value of new Address in response is: "+newAddressInResponse);

    }

    @Parameters({"requestURL"})
    @Test(priority=3)
    public void delete_BankEmployee_record(String requestURL){

        String delete_apiPathOfBankEmployee = requestURL+"/"+idFromResponse;
        Response res = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(delete_apiPathOfBankEmployee);

        //validate the status code
        String newStatus_code = String.valueOf(res.statusCode()); // converting response.statusCode from int to String
        Assert.assertEquals(newStatus_code,"200");
        System.out.println("The status code after delete call is: "+newStatus_code);

        //validate the response message after delete
        String expectedResponseMessage = "{\"message\":\"Object with id = "+idFromResponse+" has been deleted.\"}";
        String actualResponseMsg = res.asString();
        Assert.assertEquals(actualResponseMsg, expectedResponseMessage);
        System.out.println("Expected message after delete"+expectedResponseMessage);
        System.out.println("Response message after delete: "+actualResponseMsg);

    }

    @AfterClass
    public void tearDown(){

    }

}

/* output
C:\Users\sunilk\IdeaProjects\TestNG_RestAssurd>mvn clean test -Dsurefire.suiteXmlFiles=AssignmentXml/assignment002.xml
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
The status code after post call is: 200
The Id in the response is: ff8081818eeb2234018ef21180321a8b
The status code after get call is: 200
Value of name in response is: Sam Sundar
Value of year in response is: 2024
Value of dob in response is: 03-03-1985
Value of Address in response is: Kolkata 1234
Value of Salary in response is: 10000
The status code after put is: 200
Value of UpdatedAddress in response is: Delhi 1234
The status code after get call is: 200
Value of new Address in response is: Delhi 1234
The status code after delete call is: 200
Expected message after delete{"message":"Object with id = ff8081818eeb2234018ef21180321a8b has been deleted."}
Response message after delete: {"message":"Object with id = ff8081818eeb2234018ef21180321a8b has been deleted."}
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 10.033 s - in TestSuite
 */
