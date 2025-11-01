package kz.zzhalelov.staffflow.server.bonus.dto;

import jakarta.validation.constraints.*;
import kz.zzhalelov.staffflow.server.bonus.Bonus;
import kz.zzhalelov.staffflow.server.bonus.BonusEntry;
import kz.zzhalelov.staffflow.server.organization.Organization;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * DTO for {@link Bonus}
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BonusCreateDto {
    @NotNull(message = "Organization is required")
    Organization organization;
    @NotNull(message = "Month is required")
    Month month;
    @Min(message = "Min value is 2000", value = 2000)
    @Max(message = "Max value is 2050", value = 2050)
    @Positive(message = "Year value must be positive")
    int year;
    LocalDate createdAt;
    List<BonusEntry> entries;
}