package extentReportLearn;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class ExtentReport_e2e {

    private ExtentSparkReporter spark;
    private ExtentReports extent;
    private ExtentTest logger;

    @BeforeClass
    public void createSetup(){
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/ExtentReport/reqresTestExecutionReport_e2e.html"); //current path of the project using System.getProperty
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

    @Test
    public void validateSoapIdAndEmail() {
        logger.info("This is for e2e test information about firstname validation");
        logger.info("Get API Url is: "+"https://reqres.in/api/users?page=2");
        Response response = get("https://reqres.in/api/users?page=2");
        String res = response.getBody().asString();

        //first get count of node you want to test
        int jsonPathCount = response.jsonPath().getList("data.first_name").size();
        System.out.println("Count is ==>" + jsonPathCount);

        for (int i = 0; i < jsonPathCount; i++) {
            String id = response.getBody().jsonPath().getString("data.id[" + i + "]");
            String first_name = response.getBody().jsonPath().getString("data.first_name[" + i + "]");

            System.out.println("If ID is ==>" + id + " " + "Then first_name value is ==> " + first_name);
            logger.info("If ID is ==>" + id + " " + "Then first_name value is ==> " + first_name);

        }
    }
    @AfterClass
    public void tearDown(){
    extent.flush();
    }

}
