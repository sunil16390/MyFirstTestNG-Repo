package TestNGAssignment;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import resuable.CreateRequestBody;


import java.io.FileInputStream;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Assignment003 {
    CreateRequestBody RJB;
    ReadFromExcel readXLObj;
    String idFromResponse = null;

    @BeforeClass
    public void setUp(){

        readXLObj = new ReadFromExcel();
        RJB = new CreateRequestBody();
    }

    @Parameters({"requestURL"})
    @Test(priority=1)
    public void createBankEmpData(String requestURL) {
        Response res = null;
        String name = null;
        String year = null;
        String dob = null;
        String address = null;
        String salary = null;
        for(int i=1; i<=20; i++) {

            name = readXLObj.getCellData(i,0);
            year = readXLObj.getCellData(i,1);
            dob = readXLObj.getCellData(i,2);
            address = readXLObj.getCellData(i,3);
            salary = readXLObj.getCellData(i,4);

             res = given()
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

            //validate name
            String nameInResponse = res.getBody().jsonPath().getString("name");
            Assert.assertEquals(nameInResponse,name);
            System.out.println("Value of name in response is: "+nameInResponse);
            //validate year
            String yearInResponse = res.getBody().jsonPath().getString("data.year");
            Assert.assertEquals(yearInResponse,year);
            System.out.println("Value of year in response is: "+yearInResponse);
            //validate dob
            String dobInResponse = res.getBody().jsonPath().getString("data.DOB");
            Assert.assertEquals(dobInResponse,dob);
            System.out.println("Value of dob in response is: "+dobInResponse);
            //validate Address
            String addressInResponse = res.getBody().jsonPath().getString("data.Address");
            Assert.assertEquals(addressInResponse,address);
            System.out.println("Value of Address in response is: "+addressInResponse);
            //validate Salary
            String salaryInResponse = res.getBody().jsonPath().getString("data.Salary");
            Assert.assertEquals(salaryInResponse,salary);
            System.out.println("Value of Salary in response is: "+salaryInResponse);
        }

    }

    @Parameters({"requestURL"})
    @Test(priority=2)
    public void putCallToUpdateAddressOfBankEmployeeDetails_Validation(String requestURL, String name, String year, String dob, String UpdatedAddress, String salary){

        String put_apiPathOfCreatedBankEmployee = requestURL+"/"+idFromResponse;
        String get_PathOfCreatedBankEmployee = requestURL+"?id="+idFromResponse;
        Response response = get(get_PathOfCreatedBankEmployee);
        name = response.getBody().jsonPath().getString("name");
        year = response.getBody().jsonPath().getString("data.year");
        dob = response.getBody().jsonPath().getString("data.DOB");
        UpdatedAddress = "Agra 34526";
        salary = response.getBody().jsonPath().getString("data.Salary");
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

        response = get(get_PathOfCreatedBankEmployee);

        //validate the status code
        String newStatus_code = String.valueOf(response.statusCode()); // converting response.statusCode from int to String
        Assert.assertEquals(newStatus_code,"200");
        System.out.println("The status code after get call is: "+newStatus_code);

        //validate Address
        String newAddressInResponse = response.getBody().jsonPath().getString("data.Address");
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
