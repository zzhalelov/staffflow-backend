package kz.zzhalelov.staffflow.server.timesheet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.timesheet.DayStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Внесение в табель сведений о дне месяца и его статусе")
public class TimesheetDayCreateDto {
    @Schema(description = "Номер для в календаре", example = "13")
    int dayNumber;

    @Schema(description = "Статус дня", allowableValues = "PRESENT, ABSENT, VACATION, SICK_LEAVE, HOLIDAY, DAY_OFF")
    DayStatus status;
}