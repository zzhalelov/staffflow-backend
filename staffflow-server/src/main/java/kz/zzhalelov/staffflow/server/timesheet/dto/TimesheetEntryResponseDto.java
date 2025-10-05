package kz.zzhalelov.staffflow.server.timesheet.dto;

import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimesheetEntryResponseDto {
    Long id;
    Long timesheetId;
    EmployeeShortResponseDto employee;
    List<TimesheetDayResponseDto> days;
}