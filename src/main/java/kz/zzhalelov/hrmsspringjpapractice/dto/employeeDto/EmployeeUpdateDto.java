package kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeUpdateDto {
    String firstName;
    String lastName;
    String iin;
    String gender;
    String email;
    String phone;
}