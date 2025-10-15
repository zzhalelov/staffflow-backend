package kz.zzhalelov.staffflow.server.payroll;

import java.time.Month;
import java.util.List;

public interface PayrollService {
    Payroll createPayroll(Long organizationId, Month month, int year);

    Payroll addEmployeeToPayroll(Long employeeId, Long payrollId);

    List<Payroll> findAll();
}