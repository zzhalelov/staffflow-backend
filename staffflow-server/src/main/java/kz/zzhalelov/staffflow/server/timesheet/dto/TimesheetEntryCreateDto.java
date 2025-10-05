package kz.zzhalelov.staffflow.server.timesheet.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimesheetEntryCreateDto {
    Long employeeId;
    List<TimesheetDayResponseDto> days;
}