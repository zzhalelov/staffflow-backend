package kz.zzhalelov.staffflow.server.hiring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.zzhalelov.staffflow.server.hiring.HiringStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HiringUpdateDto {
    @NotNull(message = "OrganizationId is required")
    Long organizationId;
    @NotNull(message = "EmployeeId is required")
    Long employeeId;
    @NotBlank(message = "Hire date is required")
    LocalDate hireDate;
    @NotNull(message = "DepartmentId is required")
    Long departmentId;
    @NotNull(message = "PositionId is required")
    Long positionId;
    HiringStatus hiringStatus;
    @NotNull(message = "Probation period is required")
    Long probationPeriod;
    @NotBlank(message = "Comment is required")
    String comments;
}