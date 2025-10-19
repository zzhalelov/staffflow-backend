package kz.zzhalelov.staffflow.server.timesheet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Вывод сведений о записях в табличную часть табеля")
public class TimesheetEntryResponseDto {
    @Schema(description = "Идентификатор", example = "11")
    Long id;

    @Schema(description = "Id табеля учета рабочего времени", example = "1")
    Long timesheetId;

    @Schema(description = "Сведения о сотруднике")
    EmployeeShortResponseDto employee;

    @Schema(description = "Перечень календарных дней в месяце")
    List<TimesheetDayResponseDto> days;
}