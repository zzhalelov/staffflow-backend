package kz.zzhalelov.staffflow.server.sickLeave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.Month;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SickLeaveUpdateDto {
    Long id;
    @NotNull(message = "Month is required")
    Month month;
    @NotNull(message = "Year is required")
    Integer year;
    @NotBlank(message = "Start date is required")
    LocalDate startDate;
    @NotBlank(message = "End date is required")
    LocalDate endDate;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description must be not empty")
    @Length(max = 100)
    String description;
}