package kz.zzhalelov.staffflow.server.sickLeave.dto;

import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import kz.zzhalelov.staffflow.server.sickLeave.SickLeave;
import org.springframework.stereotype.Component;

@Component
public class SickLeaveMapper {
    public SickLeave fromCreate(SickLeaveCreateDto dto) {
        SickLeave sickLeave = new SickLeave();
        sickLeave.setMonth(dto.getMonth());
        sickLeave.setYear(dto.getYear());
        sickLeave.setStartDate(dto.getStartDate());
        sickLeave.setEndDate(dto.getEndDate());
        sickLeave.setDescription(dto.getDescription());
        return sickLeave;
    }

    public SickLeave fromUpdate(SickLeaveUpdateDto dto) {
        SickLeave sickLeave = new SickLeave();
        sickLeave.setMonth(dto.getMonth());
        sickLeave.setYear(dto.getYear());
        sickLeave.setStartDate(dto.getStartDate());
        sickLeave.setEndDate(dto.getEndDate());
        sickLeave.setDescription(dto.getDescription());
        return sickLeave;
    }

    public SickLeaveResponseDto toResponse(SickLeave sickLeave) {
        SickLeaveResponseDto dto = new SickLeaveResponseDto();
        dto.setId(sickLeave.getId());
        dto.setEmployee(new EmployeeShortResponseDto(
                sickLeave.getEmployee().getId(),
                sickLeave.getEmployee().getFirstName(),
                sickLeave.getEmployee().getLastName()
        ));
        dto.setOrganization(new OrganizationShortResponseDto(
                sickLeave.getOrganization().getId(),
                sickLeave.getOrganization().getShortName()
        ));
        dto.setMonth(sickLeave.getMonth());
        dto.setYear(sickLeave.getYear());
        dto.setStartDate(sickLeave.getStartDate());
        dto.setEndDate(sickLeave.getEndDate());
        dto.setStatus(sickLeave.getStatus());
        dto.setDescription(sickLeave.getDescription());
        return dto;
    }
}