package kz.zzhalelov.staffflow.server.timesheet.dto;

import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Month;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimesheetCreateDto {
    OrganizationShortResponseDto organization;
    Month month;
    int year;
    List<TimesheetEntryResponseDto> entries;
}