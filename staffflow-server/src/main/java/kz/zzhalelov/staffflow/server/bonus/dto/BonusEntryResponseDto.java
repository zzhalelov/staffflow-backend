package kz.zzhalelov.staffflow.server.bonus.dto;

import kz.zzhalelov.staffflow.server.bonus.Bonus;
import kz.zzhalelov.staffflow.server.bonus.BonusDetail;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO for {@link kz.zzhalelov.staffflow.server.bonus.BonusEntry}
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BonusEntryResponseDto {
    Long id;
    Bonus bonus;
    Employee employee;
    List<BonusDetail> details;
}