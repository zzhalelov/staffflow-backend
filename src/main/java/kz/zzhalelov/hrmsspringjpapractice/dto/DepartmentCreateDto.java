package kz.zzhalelov.hrmsspringjpapractice.dto;

import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentCreateDto {
    String name;
    Employee managerId;
}