package kz.zzhalelov.staffflow.server.earningType.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EarningTypeShortResponseDto {
    Long id;
    String name;
    String code;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean indexable;
    String description;
}
