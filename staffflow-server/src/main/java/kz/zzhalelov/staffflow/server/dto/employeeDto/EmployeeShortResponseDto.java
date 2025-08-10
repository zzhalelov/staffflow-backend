package kz.zzhalelov.staffflow.server.dto.employeeDto;

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