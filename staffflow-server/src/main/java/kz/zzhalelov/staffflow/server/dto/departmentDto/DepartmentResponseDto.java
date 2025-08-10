package kz.zzhalelov.staffflow.server.dto.departmentDto;

import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponseDto {
    String name;
    EmployeeShortResponseDto manager;
}