package kz.zzhalelov.staffflow.server.regulatoryIndicator.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegulatoryIndicatorUpdateDto {
    LocalDate date;
    BigDecimal mzpValue;
    BigDecimal mrpValue;
    String description;
}