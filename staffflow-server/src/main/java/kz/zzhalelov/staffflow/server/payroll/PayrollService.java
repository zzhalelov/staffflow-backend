package kz.zzhalelov.staffflow.server.payroll;

import java.time.Month;
import java.util.List;

public interface PayrollService {
    Payroll createPayroll(Long organizationId, Month month, int year);

    Payroll addEmployeeToPayroll(Long payrollId, Long employeeId);

    Payroll addDetail(Long payrollId, Long employeeId, Long workedDays, Long plannedDays);

    List<Payroll> findAll();

    void delete(Long payrollId);
}