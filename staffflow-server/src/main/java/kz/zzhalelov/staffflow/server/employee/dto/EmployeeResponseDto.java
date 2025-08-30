package kz.zzhalelov.staffflow.server.employee.dto;

import kz.zzhalelov.staffflow.server.employee.GenderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponseDto {
    Long id;
    String firstName;
    String lastName;
    String iin;
    GenderType gender;
    String email;
    String phone;
    String address;
    String citizenship;
}