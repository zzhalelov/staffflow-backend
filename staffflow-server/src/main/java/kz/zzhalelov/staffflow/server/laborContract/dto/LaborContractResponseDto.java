package kz.zzhalelov.staffflow.server.laborContract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractStatus;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import kz.zzhalelov.staffflow.server.position.dto.PositionResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaborContractResponseDto {
    Long id;
    OrganizationShortResponseDto organization;
    EmployeeShortResponseDto employee;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate hireDate;
    Department department;
    PositionResponseDto position;
    LaborContractStatus laborContractStatus;
}