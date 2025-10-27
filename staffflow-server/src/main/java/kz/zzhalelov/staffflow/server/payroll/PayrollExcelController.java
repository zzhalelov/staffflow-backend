package kz.zzhalelov.staffflow.server.payroll;

import kz.zzhalelov.staffflow.server.email.EmailService;
import kz.zzhalelov.staffflow.server.excelReport.PayrollExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payrolls/excel")
public class PayrollExcelController {
    private final PayrollExcelService excelService;
    private final PayrollService payrollService;
    private final EmailService emailService;

    public PayrollExcelController(PayrollExcelService excelService, PayrollService payrollService, EmailService emailService) {
        this.excelService = excelService;
        this.payrollService = payrollService;
        this.emailService = emailService;
    }

    @GetMapping("/{payrollId}")
    public ResponseEntity<byte[]> downloadPayrollExcel(@PathVariable Long payrollId) throws Exception {
        Payroll payroll = payrollService.findById(payrollId);
        byte[] excelData = excelService.generatePayslipSheet(payroll);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payslip.xlsx")
                .body(excelData);
    }

    @PostMapping("/send/{payrollId}")
    public ResponseEntity<String> sendPayrollExcel(@PathVariable Long payrollId,
                                                   @RequestParam String email) throws Exception {
        Payroll payroll = payrollService.findById(payrollId);
        byte[] excelData = excelService.generatePayslipSheet(payroll);

        emailService.sendPayslipExcel(
                email,
                "Ваш расчетный листок",
                "Здравствуйте!\n\nВо вложении ваш расчетный лист за месяц.\n\nС уважением, HR-отдел.",
                excelData
        );
        return ResponseEntity.ok("Excel-файл успешно отправлен на " + email);
    }
}