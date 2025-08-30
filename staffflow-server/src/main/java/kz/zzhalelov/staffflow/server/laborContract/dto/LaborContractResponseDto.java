package kz.zzhalelov.staffflow.server.laborContract.dto;

import kz.zzhalelov.staffflow.server.laborContract.LaborContractStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaborContractResponseDto {
    Long employeeId;
    LocalDateTime hireDate;
    Long departmentId;
    Long positionId;
    LaborContractStatus laborContractStatus;
}