package kz.zzhalelov.staffflow.server.dto.employeeDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponseDto {
    Integer id;
    String firstName;
    String lastName;
    String iin;
    String gender;
    String email;
    String phone;
    String address;
    String citizenship;
}