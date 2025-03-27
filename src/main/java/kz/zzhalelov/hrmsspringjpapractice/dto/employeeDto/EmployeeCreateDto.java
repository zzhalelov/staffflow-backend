package kz.zzhalelov.hrmsspringjpapractice.dto.employeeDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeCreateDto {
    String firstName;
    String lastName;
    String email;
    String phone;
}