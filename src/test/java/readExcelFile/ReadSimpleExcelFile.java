package readExcelFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;

public class ReadSimpleExcelFile {


    // Workbook - Interface to instantiate different XL file.[xls/xlsx]
// Sheet - Interface to read the sheet inside the Workbook
// Row - Interface used to identify the row inside the sheet.
// Cell - Interface to identify the corresponding Cell of a given Row.

// CLASSES INSIDE APACHE POI


// XSSFWorkbook - Class which will implement Workbook interface for the XL file.
// HSSFWorkbook - Class which will implement Workbook interface for the XL file
// XSSFSheet - Class representing a Sheet interface.
// HSSFSheet - Class representing a Sheet interface.
// XSSFRow - Class representing a Row interface.
// HSSFRow - Class representing a Row interface.
// XSSFCell - Class representing a Cell interface.
// HSSFCell - Class representing a Cell interface.


    @Test
    public void read_and_print_simple_XL() {
        try {
            String XLFilePath = "C:\\Users\\sunilk\\Desktop\\TestFile.xlsx";
            FileInputStream myxlfile = new FileInputStream(XLFilePath);
            Workbook workbook = new XSSFWorkbook(myxlfile);
            Sheet sheet = workbook.getSheet("USAPopulationData");
            int lastrow = sheet.getLastRowNum();
            System.out.println("The last row which has data ==" +lastrow);

            //loop for Row iteration
            for(int i=0;i<=lastrow;i++){
                Row row = sheet.getRow(i);

                //get the last column which has data
                int lastCell = row.getLastCellNum();

                //loop for column iteration
                for(int j=0;j<lastCell;j++){
                Cell cell = row.getCell(j);
                String value = cell.getStringCellValue();
                    System.out.println("The XL value is ==>"+value);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
          read_and_Print_XL_AsPerTestData("2013","Population");
    }
    public static void read_and_Print_XL_AsPerTestData(String testcaseName, String columnName) {
        try {
            String XLFilePath = "C:\\Users\\sunilk\\Desktop\\TestFile.xlsx";
            FileInputStream myxlfile = new FileInputStream(XLFilePath);
            Workbook workbook = new XSSFWorkbook(myxlfile);
            Sheet sheet = workbook.getSheet("USAPopulationData");
            int lastrow = sheet.getLastRowNum();
            System.out.println("The last row which has data ==" +lastrow);

            //loop for Row iteration
            for(int i=0;i<=lastrow;i++){
                Row row = sheet.getRow(i);
                //get the last column which has data
                int lastCell = row.getLastCellNum();
                Cell cell = row.getCell(0);
                String runtimeTestCaseName = cell.getStringCellValue();
                System.out.println("The First Column value is: "+runtimeTestCaseName);
                if(runtimeTestCaseName.equals(testcaseName))
                {
                    //loop for column iteration
                    Row RowNew = sheet.getRow(0);
                    for(int j=0;j<lastCell;j++){
                        Cell cellnew = RowNew.getCell(j);
                        String RunTimeCellValue = cellnew.getStringCellValue();
                        if(RunTimeCellValue.equals(columnName))
                        {
                            String value = sheet.getRow(i).getCell(j).toString();
                            System.out.println("The XL value is ==>"+value);
                        }

                    }
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
