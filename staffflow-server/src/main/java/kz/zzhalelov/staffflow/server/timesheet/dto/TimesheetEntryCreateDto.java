package kz.zzhalelov.staffflow.server.timesheet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Создание записи в табличной части табеля")
public class TimesheetEntryCreateDto {
    @Schema(description = "Id сотрудника", example = "1")
    Long employeeId;

    @Schema(description = "Дни по табелю")
    List<TimesheetDayResponseDto> days;
}