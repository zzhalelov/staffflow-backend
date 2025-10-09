package kz.zzhalelov.staffflow.server.earning.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EarningTypeCreateDto {
    String name;
    String code;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean isIndexable;
    String description;
}