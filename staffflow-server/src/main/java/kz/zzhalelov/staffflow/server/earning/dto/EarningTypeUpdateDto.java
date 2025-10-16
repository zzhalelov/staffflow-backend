package kz.zzhalelov.staffflow.server.earning.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EarningTypeUpdateDto {
    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Code is required")
    String code;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean isIndexable;
    String description;
}