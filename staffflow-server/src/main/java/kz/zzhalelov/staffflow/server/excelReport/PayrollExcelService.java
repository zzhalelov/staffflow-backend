package kz.zzhalelov.staffflow.server.excelReport;

import kz.zzhalelov.staffflow.server.payroll.Payroll;
import kz.zzhalelov.staffflow.server.payroll.PayrollDetail;
import kz.zzhalelov.staffflow.server.payroll.PayrollEntry;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Month;

@Service
public class PayrollExcelService {
    public byte[] generatePayslipSheet(Payroll payroll) throws Exception {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("PaySlip");
            int rowIdx = 0;

            // --- Стили ---
            CellStyle bold = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            bold.setFont(boldFont);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            CellStyle numStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            numStyle.setDataFormat(format.getFormat("#,##0.00"));

            // --- Заголовок: организация, месяц, год ---
            Row orgRow = sheet.createRow(rowIdx++);
            orgRow.createCell(0).setCellValue("Организация");
            orgRow.getCell(0).setCellStyle(bold);
            orgRow.createCell(1).setCellValue(payroll.getOrganization().getShortName());
            orgRow.createCell(3).setCellValue("Месяц");
            orgRow.getCell(3).setCellStyle(bold);
            orgRow.createCell(4).setCellValue(getMonthNameRu((payroll.getMonth()) + " " + payroll.getYear()));

            rowIdx++;

            // --- Данные сотрудника ---
            Row employeeRow = sheet.createRow(rowIdx++);
            employeeRow.createCell(0).setCellValue("Сотрудник:");
            employeeRow.getCell(0).setCellStyle(bold);
            PayrollEntry entry = payroll.getEntries().get(0);
            employeeRow.createCell(1).setCellValue(entry.getEmployee().getFirstName() + " " + entry.getEmployee().getLastName());

            rowIdx++;

            // --- Таблица начислений ---
            Row header = sheet.createRow(rowIdx++);
            String[] headers = {"Вид начисления", "Дней (факт/план)", "Начислено", "ОПВ", "ВОСМС", "ИПН", "К выдаче"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            for (PayrollDetail d : entry.getDetails()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(d.getEarningType().getName());
                row.createCell(1).setCellValue(d.getWorkedDays() + " / " + d.getPlannedDays());

                Cell c2 = row.createCell(2);
                c2.setCellValue(d.getGrossSum() != null ? d.getGrossSum().doubleValue() : 0.0);
                c2.setCellStyle(numStyle);

                Cell c3 = row.createCell(3);
                c3.setCellValue(d.getOpv() != null ? d.getOpv().doubleValue() : 0.0);
                c3.setCellStyle(numStyle);

                Cell c4 = row.createCell(4);
                c4.setCellValue(d.getVosms() != null ? d.getVosms().doubleValue() : 0.0);
                c4.setCellStyle(numStyle);

                Cell c5 = row.createCell(5);
                c5.setCellValue(d.getIpn() != null ? d.getIpn().doubleValue() : 0.0);
                c5.setCellStyle(numStyle);

                Cell c6 = row.createCell(6);
                c6.setCellValue(d.getNetSum() != null ? d.getNetSum().doubleValue() : 0.0);
                c6.setCellStyle(numStyle);
            }

            // --- Авторазмер ---
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public String getMonthNameRu(String monthEng) {
        String normalized = monthEng.split(" ")[0].trim().toUpperCase();
        Month month = Month.valueOf(normalized);
        return switch (month) {
            case JANUARY -> "Январь";
            case FEBRUARY -> "Февраль";
            case MARCH -> "Март";
            case APRIL -> "Апрель";
            case MAY -> "Май";
            case JUNE -> "Июнь";
            case JULY -> "Июль";
            case AUGUST -> "Август";
            case SEPTEMBER -> "Сентябрь";
            case OCTOBER -> "Октябрь";
            case NOVEMBER -> "Ноябрь";
            case DECEMBER -> "Декабрь";
        };
    }
}