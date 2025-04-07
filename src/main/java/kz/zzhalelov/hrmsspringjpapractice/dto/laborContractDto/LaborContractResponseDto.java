package kz.zzhalelov.hrmsspringjpapractice.dto.laborContractDto;

import kz.zzhalelov.hrmsspringjpapractice.model.LaborContractStatus;
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