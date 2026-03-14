package kz.zzhalelov.staffflow.server.earningType.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EarningTypeFullResponseDto {
    Long id;
    String name;
    String code;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean indexable;
    String description;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    boolean deleted;
    LocalDateTime deletedAt;
    String deletedBy;
}