package kz.zzhalelov.staffflow.server.absence.vacation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.zzhalelov.staffflow.server.absence.AbsenceStatus;
import kz.zzhalelov.staffflow.server.absence.vacation.VacationType;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Month;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacationUpdateDto {
    @NotNull(message = "Employee is required")
    Employee employee;
    @NotNull(message = "Status is required")
    AbsenceStatus status;
    @NotBlank(message = "Vacation type is required")
    VacationType vacationType;
    @NotNull(message = "Month is required")
    Month month;
    @NotBlank(message = "Year is required")
    int year;
    @NotNull(message = "Start date is required")
    LocalDate startDate;
    @NotNull(message = "End date is required")
    LocalDate endDate;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description must be not empty")
    @Length(max = 100)
    String description;
}