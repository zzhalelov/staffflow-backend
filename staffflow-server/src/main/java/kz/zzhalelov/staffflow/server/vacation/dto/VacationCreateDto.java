package kz.zzhalelov.staffflow.server.vacation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.zzhalelov.staffflow.server.vacation.Vacation;
import kz.zzhalelov.staffflow.server.vacation.VacationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Month;

/**
 * DTO for {@link Vacation}
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacationCreateDto {
    @NotBlank(message = "VacationType type is required")
    VacationType vacationType;
    @NotNull(message = "Month is required")
    Month month;
    @NotNull(message = "Year is required")
    Integer year;
    @NotNull(message = "Start date is required")
    LocalDate startDate;
    @NotNull(message = "End date is required")
    LocalDate endDate;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description must be not empty")
    @Length(max = 100)
    String description;
}