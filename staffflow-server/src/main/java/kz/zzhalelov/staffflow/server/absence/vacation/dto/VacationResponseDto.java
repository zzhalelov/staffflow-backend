package kz.zzhalelov.staffflow.server.absence.vacation.dto;

import kz.zzhalelov.staffflow.server.absence.AbsenceStatus;
import kz.zzhalelov.staffflow.server.absence.vacation.Vacation;
import kz.zzhalelov.staffflow.server.absence.vacation.VacationType;
import kz.zzhalelov.staffflow.server.employee.Employee;
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
    Employee employee;
    AbsenceStatus status;
    VacationType vacationType;
    Month month;
    int year;
    LocalDate startDate;
    LocalDate endDate;
    String description;
}