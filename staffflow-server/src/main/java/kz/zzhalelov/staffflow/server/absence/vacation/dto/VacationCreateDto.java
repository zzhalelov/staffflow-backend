package kz.zzhalelov.staffflow.server.absence.vacation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.zzhalelov.staffflow.server.absence.AbsenceStatus;
import kz.zzhalelov.staffflow.server.absence.vacation.Vacation;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Month;

/**
 * DTO for {@link Vacation}
 */
@Value
public class VacationCreateDto {
    @NotNull(message = "Employee is required")
    Employee employee;
    @NotNull(message = "Status is required")
    AbsenceStatus status;
    @NotNull(message = "Month is required")
    Month month;
    @NotNull(message = "Start date is required")
    LocalDate startDate;
    @NotNull(message = "End date is required")
    LocalDate endDate;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description must be not empty")
    @Length(max = 100)
    String description;
}