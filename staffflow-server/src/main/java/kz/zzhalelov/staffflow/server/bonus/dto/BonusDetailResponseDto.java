package kz.zzhalelov.staffflow.server.bonus.dto;

import kz.zzhalelov.staffflow.server.bonus.BonusEntry;
import kz.zzhalelov.staffflow.server.earningType.EarningType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * DTO for {@link kz.zzhalelov.staffflow.server.bonus.BonusDetail}
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BonusDetailResponseDto {
    Long id;
    BonusEntry bonusEntry;
    EarningType earningType;
    BigDecimal amount;
}