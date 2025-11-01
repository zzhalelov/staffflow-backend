package kz.zzhalelov.staffflow.server.excelReport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelExample {
    public static void main(String[] args) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Example");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Hello");  // Ячейка A1 со словом "Привет"
            row.createCell(1).setCellValue("World");  // И ячейка B1 с изложением "Мир"

            try (FileOutputStream out = new FileOutputStream("Example.xlsx")) {
                workbook.write(out);
            }
        }
    }
}