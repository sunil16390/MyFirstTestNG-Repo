package resuable;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;

public class ReadFromXL {
    @BeforeSuite
    public void run_Before_Suite(){

        System.out.println("This is Before Suite .....");
    }

    @AfterSuite
    public void run_After_Suite(){

        System.out.println("This is After Suite .....");
    }
    public String getCellData(int rowNum, int colNum) {
        String value = null;
        try {
            String XLFilePath = System.getProperty("user.dir")+"/InputFiles/PetDir.xlsx";
            FileInputStream myxlfile = new FileInputStream(XLFilePath);
            Workbook workbook = new XSSFWorkbook(myxlfile);
            Sheet sheet = workbook.getSheet("PetDir");
            DataFormatter formatter = new DataFormatter();
            value = formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum)).toString();
           // System.out.println("value: " + value);
        }
        catch (Exception e) {
            System.out.println("Exception Message: "+e.getMessage());
            System.out.println("Exception cause: "+e.getCause());
        }
        return value;
    }

}
