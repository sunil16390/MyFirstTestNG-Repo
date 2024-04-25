package TestNGAssignment;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUlits {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public ExcelUlits(String excelPath, String sheetName){
        try {
            workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet(sheetName);
        }
        catch (Exception e) {
            System.out.println("Exception Message: "+e.getMessage());
            System.out.println("Exception cause: "+e.getCause());
        }
    }
    public static Object getCellData(int rowNum, int colNum) {
        DataFormatter formatter = new DataFormatter();
        Object value = formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
        System.out.println("value: "+value);
        return value;
    }
}
