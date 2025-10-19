package kz.zzhalelov.staffflow.server.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Создание записи в табличной части начисления заработной платы")
public class PayrollEntryCreateDto {
    @Schema(description = "Id сотрудника", example = "1")
    Long employeeId;

    @Schema(description = "Детали начисления")
    List<PayrollDetailResponseDto> details;
}
