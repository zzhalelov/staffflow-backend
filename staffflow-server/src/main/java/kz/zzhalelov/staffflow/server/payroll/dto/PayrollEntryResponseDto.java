package kz.zzhalelov.staffflow.server.payroll.dto;

import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayrollEntryResponseDto {
    Long id;
    Long payrollId;
    EmployeeShortResponseDto employee;
}