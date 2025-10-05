package kz.zzhalelov.staffflow.server.timesheet.dto;

import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import kz.zzhalelov.staffflow.server.timesheet.Timesheet;
import kz.zzhalelov.staffflow.server.timesheet.TimesheetDay;
import kz.zzhalelov.staffflow.server.timesheet.TimesheetEntry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class TimesheetMapper {
    public Timesheet fromCreate(TimesheetCreateDto dto) {
        Timesheet timesheet = new Timesheet();
        timesheet.setMonth(dto.getMonth());
        timesheet.setYear(dto.getYear());
        timesheet.setCreatedAt(LocalDateTime.now());
        return timesheet;
    }

    public TimesheetEntry toEntry(TimesheetEntryCreateDto dto) {
        TimesheetEntry entry = new TimesheetEntry();
        return entry;
    }

    public TimesheetDay toDay(TimesheetDayCreateDto dto) {
        TimesheetDay day = new TimesheetDay();
        day.setDayOfMonth(dto.getDayNumber());
        day.setStatus(dto.getStatus());
        return day;
    }

    public TimesheetResponseDto toResponseDto(Timesheet entity) {
        TimesheetResponseDto dto = new TimesheetResponseDto();
        dto.setId(entity.getId());
        //Organization - just short DTO
        Organization org = entity.getOrganization();
        if (org != null) {
            OrganizationShortResponseDto orgDto = new OrganizationShortResponseDto();
            orgDto.setId(org.getId());
            orgDto.setName(org.getShortName());
            dto.setOrganization(orgDto);
        }

        dto.setMonth(entity.getMonth());
        dto.setYear(entity.getYear());
        dto.setCreatedAt(entity.getCreatedAt());

        //Entries
        if (entity.getEntries() != null) {
            dto.setEntries(entity.getEntries()
                    .stream()
                    .map(this::toEntryResponse)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private TimesheetEntryResponseDto toEntryResponse(TimesheetEntry entry) {
        TimesheetEntryResponseDto dto = new TimesheetEntryResponseDto();
        dto.setId(entry.getId());

        // Employee → только id и имя
        Employee employee = entry.getEmployee();
        if (employee != null) {
            EmployeeShortResponseDto employeeDto = new EmployeeShortResponseDto();
            employeeDto.setId(employee.getId());
            employeeDto.setFirstName(employee.getFirstName());
            employeeDto.setLastName(employee.getLastName());
            dto.setEmployee(employee);
        }
        if (entry.getDays() != null) {
            dto.setDays(entry.getDays()
                    .stream()
                    .map(this::toDayResponse)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private TimesheetDayResponseDto toDayResponse(TimesheetDay day) {
        TimesheetDayResponseDto dto = new TimesheetDayResponseDto();
        dto.setId(day.getId());
        dto.setDayNumber(day.getDayOfMonth());
        dto.setStatus(day.getStatus());
        return dto;
    }
}