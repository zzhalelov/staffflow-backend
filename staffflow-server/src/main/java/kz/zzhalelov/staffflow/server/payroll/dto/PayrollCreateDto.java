package kz.zzhalelov.staffflow.server.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Month;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Создание начисления заработной платы")
public class PayrollCreateDto {
    @Schema(description = "", example = "1")
    OrganizationShortResponseDto organization;

    @Schema(description = "Месяц года", allowableValues = "JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, " +
            "AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER")
    Month month;

    @Schema(description = "Год", example = "2025")
    int year;

    @Schema(description = "Список записей в табличной части начисления заработной платы")
    List<PayrollEntryResponseDto> entries;
}