package kz.zzhalelov.staffflow.server.timesheet.dto;

import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimesheetEntryResponseDto {
    Long id;
    Employee employee;
    List<TimesheetDayResponseDto> days;
}