package kz.zzhalelov.staffflow.server.vacation.dto;

import kz.zzhalelov.staffflow.server.common.AbsenceStatus;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import kz.zzhalelov.staffflow.server.vacation.Vacation;
import kz.zzhalelov.staffflow.server.vacation.VacationType;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.Month;

/**
 * DTO for {@link Vacation}
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacationResponseDto {
    Long id;
    EmployeeShortResponseDto employee;
    OrganizationShortResponseDto organization;
    AbsenceStatus status;
    VacationType vacationType;
    Month month;
    Integer year;
    LocalDate startDate;
    LocalDate endDate;
    String description;
}