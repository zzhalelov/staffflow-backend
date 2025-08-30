package kz.zzhalelov.staffflow.server.department.dto;

import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponseDto {
    String name;
    EmployeeShortResponseDto manager;
}