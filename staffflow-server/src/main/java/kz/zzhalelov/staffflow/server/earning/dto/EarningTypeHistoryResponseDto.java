package kz.zzhalelov.staffflow.server.earning.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EarningTypeHistoryResponseDto {
    Long id;
    LocalDate startDate;
    LocalDate endDate;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean isIndexable;
    String comment;
}