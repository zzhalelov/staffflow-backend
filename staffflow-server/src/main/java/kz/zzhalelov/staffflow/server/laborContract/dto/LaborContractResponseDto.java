package kz.zzhalelov.staffflow.server.laborContract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractStatus;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.position.Position;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaborContractResponseDto {
    Long id;
    Organization organization;
    Employee employee;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate hireDate;
    Department department;
    Position position;
    LaborContractStatus laborContractStatus;
}