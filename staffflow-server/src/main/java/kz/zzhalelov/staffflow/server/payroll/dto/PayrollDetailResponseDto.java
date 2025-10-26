package kz.zzhalelov.staffflow.server.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.earningType.dto.EarningTypeResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Вывод инфо о начислении")
public class PayrollDetailResponseDto {
    @Schema(description = "Идентификатор", example = "32")
    Long id;
    EarningTypeResponseDto earningType;
    BigDecimal amount;
    Long workedDays;
    Long plannedDays;
    BigDecimal grossSum;
    BigDecimal opv;
    BigDecimal vosms;
    BigDecimal ipn;
    BigDecimal netSum;
}