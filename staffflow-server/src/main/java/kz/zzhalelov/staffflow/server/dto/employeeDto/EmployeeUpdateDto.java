package kz.zzhalelov.staffflow.server.dto.employeeDto;

import kz.zzhalelov.staffflow.server.model.GenderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeUpdateDto {
    String firstName;
    String lastName;
    String iin;
    GenderType gender;
    String email;
    String phone;
    String address;
    String citizenship;
}