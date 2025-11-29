package kz.zzhalelov.staffflow.server.earningType.dto;

import kz.zzhalelov.staffflow.server.earningType.EarningType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EarningTypeHistoryResponseDto {
    Long id;
    EarningType earningType;
    LocalDate startDate;
    LocalDate endDate;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean isIndexable;
    String comment;
}