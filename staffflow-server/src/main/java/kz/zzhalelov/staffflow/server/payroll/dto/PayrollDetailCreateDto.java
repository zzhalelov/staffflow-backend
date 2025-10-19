package kz.zzhalelov.staffflow.server.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.earningType.EarningType;
import kz.zzhalelov.staffflow.server.payroll.PayrollEntry;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Заполнение сведений о начислении")
public class PayrollDetailCreateDto {
    PayrollEntry payrollEntry;
    EarningType earningType;
    BigDecimal amount;
    Long workedDays;
    Long plannedDays;
    BigDecimal grossSum;
}
