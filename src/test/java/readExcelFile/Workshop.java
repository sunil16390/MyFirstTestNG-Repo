package readExcelFile;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.get;

public class Workshop {
    String year=null;
    String population,statuscode,title;
    Support obj;
    private ExtentSparkReporter spark;
    private ExtentReports extent;
    private ExtentTest logger;

    @BeforeClass
    public void setUp(){

        obj=new Support();

        extent = new ExtentReports();
        spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/ExtentReport/USA_Population_API.html"); //current path of the project using System.getProperty
//System.getProperty("user.dir") == C:\Users\sunilk\IdeaProjects\TestNG_RestAssurd\ExtentReport
        spark.config().setDocumentTitle("https://datausa.io/api/data?drilldowns=Nation&measures=Population GET API call");
        spark.config().setReportName("get call to \"https://datausa.io/api/data?drilldowns=Nation&measures=Population\" and validate response");
        spark.config().setTheme(Theme.STANDARD);
        extent.attachReporter(spark);
        extent.setSystemInfo("QA Name", "Sunil");
        extent.setSystemInfo("Build name", "16.3.2");
        extent.setSystemInfo("ENV Name", "NAM UAT-2");
        logger = extent.createTest("Validate the uas population when year is given");
    }

    @Test
    public void Do_Web_API_Validation(){

        String key ="url";
        String url = read_Properties_file(key);
        Response response = get(url);
        String res = response.getBody().asString();
        logger.info("This is for usa population test information validation");
        logger.info("Get API Url is: "+"https://datausa.io/api/data?drilldowns=Nation&measures=Population");

        int year_path_count = response.getBody().jsonPath().getList("data.Year").size();
       // System.out.println("Total years in the response: " +year_path_count);
        for(int i =1; i <year_path_count; i++)
        {
            String years =response.getBody().jsonPath().getString("data.Year["+i+"]");
            String population = response.getBody().jsonPath().getString("data.Population["+i+"]");

            System.out.println("When year is: "+years+ " then API population is: "+population );
            String XL_population = obj.read_And_Print_XL_AsPerTestData(years,"Population");
            if (XL_population!=null) {

                Assert.assertEquals(population.trim(), XL_population.trim());
                System.out.println("When year is: "+years+ " then Excel population is: "+population );
                logger.info("When year is: "+years+ " then Excel population is: "+population);


            }
            else {
                System.out.println("The data for year "+years+ " is null in excel, please add it");
                logger.info("The data for year "+years+ " is null in excel, please add it" );
            }


        }

        // First get the count of node you want to test ...
        //Assert.assertEquals(String.valueOf(response.getStatusCode()),population);



    }
    public String read_Properties_file(String key)
    {
        Properties prop = new Properties();
        String value = null;
        try {
            prop.load(new FileInputStream(System.getProperty("user.dir") + "/usadataURL.properties"));
            value = prop.getProperty(key);

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return value;
    }
    @AfterClass
    public void tearDown(){
        extent.flush();
    }

}
