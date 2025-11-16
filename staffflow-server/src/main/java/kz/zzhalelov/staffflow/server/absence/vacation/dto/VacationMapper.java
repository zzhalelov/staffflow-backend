package kz.zzhalelov.staffflow.server.absence.vacation.dto;

import kz.zzhalelov.staffflow.server.absence.vacation.Vacation;
import org.springframework.stereotype.Component;

@Component
public class VacationMapper {
    public Vacation fromCreate(VacationCreateDto dto) {
        Vacation vacation = new Vacation();
        vacation.setEmployee(dto.getEmployee());
        vacation.setMonth(dto.getMonth());
        vacation.setYear(dto.getYear());
        vacation.setStartDate(dto.getStartDate());
        vacation.setEndDate(dto.getEndDate());
        vacation.setStatus(dto.getStatus());
        vacation.setVacationType(dto.getVacationType());
        vacation.setDescription(dto.getDescription());
        return vacation;
    }

    public Vacation fromUpdate(VacationUpdateDto dto) {
        Vacation vacation = new Vacation();
        vacation.setEmployee(dto.getEmployee());
        vacation.setMonth(dto.getMonth());
        vacation.setYear(dto.getYear());
        vacation.setStartDate(dto.getStartDate());
        vacation.setEndDate(dto.getEndDate());
        vacation.setStatus(dto.getStatus());
        vacation.setVacationType(dto.getVacationType());
        vacation.setDescription(dto.getDescription());
        return vacation;
    }

    public VacationResponseDto toResponse(Vacation vacation) {
        VacationResponseDto dto = new VacationResponseDto();
        dto.setId(vacation.getId());
        dto.setEmployee(vacation.getEmployee());
        dto.setMonth(vacation.getMonth());
        dto.setYear(vacation.getYear());
        dto.setStartDate(vacation.getStartDate());
        dto.setEndDate(vacation.getEndDate());
        dto.setStatus(vacation.getStatus());
        dto.setVacationType(vacation.getVacationType());
        dto.setDescription(vacation.getDescription());
        return dto;
    }
}