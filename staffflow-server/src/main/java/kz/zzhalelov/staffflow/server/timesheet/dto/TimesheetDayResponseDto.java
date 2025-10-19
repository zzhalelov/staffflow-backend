package kz.zzhalelov.staffflow.server.timesheet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.timesheet.DayStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Вывод инфо о днях и статусах дней в табеле")
public class TimesheetDayResponseDto {
    @Schema(description = "Идентификатор", example = "32")
    Long id;

    @Schema(description = "Номер дня (соответствует номеру дня в календаре)", example = "12")
    int dayNumber;

    @Schema(description = "Статус дня", allowableValues = "JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, " +
            "AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER")
    DayStatus status;
}