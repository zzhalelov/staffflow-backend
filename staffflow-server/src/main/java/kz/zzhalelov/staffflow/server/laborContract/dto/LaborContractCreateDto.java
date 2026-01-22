package kz.zzhalelov.staffflow.server.laborContract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaborContractCreateDto {
    @NotNull(message = "Organization Id is required")
    Long organizationId;
    @NotNull(message = "Employee Id is required")
    Long employeeId;
    @NotNull(message = "Hire date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate hireDate;
    @NotNull(message = "Department Id is required")
    Long departmentId;
    @NotNull(message = "Position Id is required")
    Long positionId;
    @NotNull(message = "LaborContractStatus is required")
    LaborContractStatus laborContractStatus;
}