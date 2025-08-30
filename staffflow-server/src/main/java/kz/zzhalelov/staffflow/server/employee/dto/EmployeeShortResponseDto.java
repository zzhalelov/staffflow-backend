package kz.zzhalelov.staffflow.server.employee.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeShortResponseDto {
    Long id;
    String firstName;
    String lastName;
}