package kz.zzhalelov.staffflow.server.timesheet.dto;

import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimesheetResponseDto {
    Long id;
    OrganizationShortResponseDto organization;
    Month month;
    int year;
    LocalDateTime createdAt;
    List<TimesheetEntryResponseDto> entries;
}