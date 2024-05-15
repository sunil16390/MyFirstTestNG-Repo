package RestAPIAssignment;

import TestNGAssignment.ReadFromExcel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import resuable.CreateRequestBody;
import resuable.ReadFromXL;

import java.util.HashMap;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Assignment001 {
    CreateRequestBody RJB;
    ReadFromXL readXLObj;

    @BeforeClass
    public void setUp(){

        readXLObj = new ReadFromXL();
        RJB = new CreateRequestBody();
    }

    @Parameters({"requestURL"})
    @Test(priority=1)
    public void createPetDetails(String requestURL) {
        Response res = null;
        String petID = null;
        String petName = null;
        HashMap<String, String> petDir = new HashMap<String, String>();
        for(int i=1; i<=20; i++) {

            petID = readXLObj.getCellData(i, 0);
            petName = readXLObj.getCellData(i, 1);
            petDir.put(petID, petName);
        }
        for (String i : petDir.keySet()) {
           // System.out.println("petID: " + i + " petName: " + petDir.get(i));
            res = given()
                    .contentType(ContentType.JSON)
                    .body(RJB.createPetDetailRequestBody(i,petDir.get(i)))
                    .when()
                    .post(requestURL);

            //validate the status code
            int status_code = res.getStatusCode();
            Assert.assertEquals(status_code,200);
            System.out.println("The status code after post call is: "+status_code);

            //validate that response body should contains text as "available"
            String statusInResponse = res.getBody().jsonPath().getString("status");
            Assert.assertEquals(statusInResponse,"available");
            System.out.println("Response body contains text as "+statusInResponse);

        }
    }


    //validate petID
    @Parameters({"requestURL","petId","petName"})
    @Test(priority=2)
    public void getCallToPetDetails(String requestURL, String petId, String petName){
        String get_PathOfPetDetails = requestURL+"/"+petId;
        //Get call to validate petDetails
        Response response = get(get_PathOfPetDetails);
        System.out.println("GET call to validate response of petID: "+petId);
        //validate id
        String idInResponse = response.getBody().jsonPath().getString("id");
        Assert.assertEquals(idInResponse,petId);
        System.out.println("Value of id in response is: "+idInResponse);

        //validate name
        String nameInResponse = response.getBody().jsonPath().getString("name");
        Assert.assertEquals(nameInResponse,petName);
        System.out.println("Value of name in response is: "+nameInResponse);
    }

    //Delete petID
    @Parameters({"requestURL","petId"})
    @Test(priority=3)
    public void delete_petDetails_record(String requestURL, String petId){

        String delete_apiPathOfPetDetails = requestURL+"/"+petId;
        Response res = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(delete_apiPathOfPetDetails);
        System.out.println("DELETE call validation ");
        //validate the status code
        String newStatus_code = String.valueOf(res.statusCode()); // converting response.statusCode from int to String
        Assert.assertEquals(newStatus_code,"200");
        System.out.println("The status code after delete call is: "+newStatus_code);

        //validate the response message after delete
        String expectedResponseMessage = "{\"code\":200,\"type\":\"unknown\",\"message\":\""+petId+"\"}";
        String actualResponseMsg = res.asString();
        Assert.assertEquals(actualResponseMsg, expectedResponseMessage);
        System.out.println("Expected message after delete"+expectedResponseMessage);
        System.out.println("Response message after delete: "+actualResponseMsg);

    }
}

//Output
/*
C:\Users\sunilk\IdeaProjects\TestNG_RestAssurd>mvn clean test -Dsurefire.suiteXmlFiles=AssignmentXml/RestAPI_assignment001.xml
[INFO] Scanning for projects...
[INFO]
[INFO] -------------------< org.example:TestNG_RestAssurd >--------------------
[INFO] Building TestNG_RestAssurd 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- clean:3.2.0:clean (default-clean) @ TestNG_RestAssurd ---
[INFO] Deleting C:\Users\sunilk\IdeaProjects\TestNG_RestAssurd\target
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ TestNG_RestAssurd ---
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO]
[INFO] --- compiler:3.11.0:compile (default-compile) @ TestNG_RestAssurd ---
[INFO] Changes detected - recompiling the module! :source
[INFO] Compiling 1 source file with javac [debug target 17] to target\classes
[INFO]
[INFO] --- resources:3.3.1:testResources (default-testResources) @ TestNG_RestAssurd ---
[INFO] skip non existing resourceDirectory C:\Users\sunilk\IdeaProjects\TestNG_RestAssurd\src\test\resources
[INFO]
[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ TestNG_RestAssurd ---
[INFO] Changes detected - recompiling the module! :dependency
[INFO] Compiling 21 source files with javac [debug target 17] to target\test-classes
[INFO]
[INFO] --- surefire:3.0.0-M5:test (default-test) @ TestNG_RestAssurd ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
The status code after post call is: 200
Response body contains text as available
GET call to validate response of petID: 2003
Value of id in response is: 2003
Value of name in response is: Tiger
DELETE call validation
The status code after delete call is: 200
Expected message after delete{"code":200,"type":"unknown","message":"2003"}
Response message after delete: {"code":200,"type":"unknown","message":"2003"}
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 29.018 s - in TestSuite
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
 */