package kz.zzhalelov.staffflow.server.dto.laborContractDto;

import kz.zzhalelov.staffflow.server.model.LaborContractStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaborContractResponseDto {
    Integer employeeId;
    LocalDateTime hireDate;
    Integer departmentId;
    Integer positionId;
    LaborContractStatus laborContractStatus;
}