package kz.zzhalelov.staffflow.server.timesheet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Вывод сведений о табеле")
public class TimesheetResponseDto {
    @Schema(description = "Идентификатор табеля", example = "1")
    Long id;

    @Schema(description = "Организация", example = "1")
    OrganizationShortResponseDto organization;

    @Schema(description = "Месяц года", allowableValues = "JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, " +
            "AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER")
    Month month;

    @Schema(description = "Год", example = "2025")
    int year;

    @Schema(description = "Дата создания табеля", example = "2025-10-19T07:01:03.799Z")
    LocalDate createdAt;

    @Schema(description = "Список записей в табличной части табеля")
    List<TimesheetEntryResponseDto> entries;
}