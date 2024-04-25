package addNewProduct;

import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import resuable.CreateRequestBody;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.*;
public class ExtentReport {

    private ExtentSparkReporter spark;
    private ExtentReports extent;
    private ExtentTest logger;

    @BeforeClass
    public void createSetup(){
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/ExtentReport/RestFullAndReqresTestExecutionReport.html"); //current path of the project using System.getProperty
//System.getProperty("user.dir") == C:\Users\sunilk\IdeaProjects\TestNG_RestAssurd\ExtentReport
        spark.config().setDocumentTitle("api.restful-api.dev GET API call");
        spark.config().setReportName("get call to \"https://api.restful-api.dev/objects\" and validate response");
        spark.config().setTheme(Theme.STANDARD);
        extent.attachReporter(spark);
        extent.setSystemInfo("QA Name", "Sunil");
        extent.setSystemInfo("Build name", "16.3.2");
        extent.setSystemInfo("ENV Name", "NAM UAT-2");
        logger = extent.createTest("Validate the mobile names when a id is given");


    }

    @Parameters({"expected_id","expectedMobileName"})
    @Test
    public void user_validates_if_id_equals_to_then_mobile_name_is(String expected_id, String expectedMobileName) {
        Response response = get("https://api.restful-api.dev/objects");
        int id_path_count = response.getBody().jsonPath().getList("id").size();

        for(int i =0; i <=id_path_count; i++)
        {
            String idOf_i =response.getBody().jsonPath().getString("id["+i+"]");
            if(idOf_i.equals(expected_id))
            {
                // Assert.assertEquals(idOf_i,expected_id);
                String nameOf_i = response.getBody().jsonPath().getString("name["+i+"]");
                Assert.assertEquals(nameOf_i,expectedMobileName);
                System.out.println("The expected id is " +idOf_i+ " and expected name is: "+nameOf_i);
                logger.info("The expected id is " +idOf_i+ " and expected name is: "+nameOf_i);
                break;
            }
        }
    }

    @Test
    public void validateSoapIdAndEmail() {
        logger.info("Get API Url is: "+"https://reqres.in/api/users?page=2");
        Response response = get("https://reqres.in/api/users?page=2");
        String res = response.getBody().asString();

        //first get count of node you want to test
        int jsonPathCount = response.jsonPath().getList("data.first_name").size();
        System.out.println("Count is ==>" + jsonPathCount);

        for (int i = 0; i < jsonPathCount; i++) {
            String id = response.getBody().jsonPath().getString("data.id[" + i + "]");
            String email = response.getBody().jsonPath().getString("data.email[" + i + "]");

            System.out.println("If ID is ==>" + id + " " + "Then Email value is ==> " + email);
            logger.info("If ID is ==>" + id + " " + "Then Email value is ==> " + email);

        }
    }
    @AfterClass
    public void tearDown(){
    extent.flush();
    }

}
