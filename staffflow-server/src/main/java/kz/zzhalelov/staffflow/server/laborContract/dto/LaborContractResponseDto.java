package kz.zzhalelov.staffflow.server.laborContract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaborContractResponseDto {
    Long id;
    Long organizationId;
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate hireDate;
    Long departmentId;
    Long positionId;
    LaborContractStatus laborContractStatus;
}