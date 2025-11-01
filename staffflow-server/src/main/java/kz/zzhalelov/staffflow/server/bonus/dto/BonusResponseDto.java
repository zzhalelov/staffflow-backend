package kz.zzhalelov.staffflow.server.bonus.dto;

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
public class BonusResponseDto {
    Long id;
    Organization organization;
    Month month;
    int year;
    LocalDate createdAt;
    List<BonusEntry> entries;
}