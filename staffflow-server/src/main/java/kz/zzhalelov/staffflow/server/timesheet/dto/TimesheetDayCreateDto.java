package kz.zzhalelov.staffflow.server.timesheet.dto;

import kz.zzhalelov.staffflow.server.timesheet.DayStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimesheetDayCreateDto {
    int dayNumber;
    DayStatus status;
}