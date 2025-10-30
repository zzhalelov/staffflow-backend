package kz.zzhalelov.staffflow.server.regulatoryIndicator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Добавление регл.показателя")
public class RegulatoryIndicatorCreateDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date is required")
    @Schema(description = "Дата", example = "2025-01-01")
    LocalDate date;

    @NotNull(message = "MZP value is required")
    @Schema(description = "Значение МЗП", example = "85000.00")
    BigDecimal mzpValue;

    @NotNull(message = "MRP value is required")
    @Schema(description = "Значение МРП", example = "3932.00")
    BigDecimal mrpValue;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 50)
    @Schema(description = "Описание", example = "МРП и МЗП на 2025 год")
    String description;
}