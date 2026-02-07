package kz.zzhalelov.staffflow.server.sickLeave.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.common.AbsenceStatus;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.Month;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Информация о листе временной нетрудоспособности")
public class SickLeaveResponseDto {
    @Schema(description = "Идентификатор листа временной нетрудоспособности", example = "12")
    Long id;
    @Schema(description = "Информация о сотруднике", example = "12")
    EmployeeShortResponseDto employee;
    @Schema(description = "Информация об организации", example = "12")
    OrganizationShortResponseDto organization;
    @Schema(description = "Статус больничного листа", example = "APPROVED")
    AbsenceStatus status;
    @Schema(description = "Месяц года", example = "JANUARY")
    Month month;
    @Schema(description = "Год", example = "2026")
    Integer year;
    @Schema(description = "Дата начала", example = "2026-01-05")
    LocalDate startDate;
    @Schema(description = "Дата окончания", example = "2026-01-11")
    LocalDate endDate;
    @Schema(description = "Описание", example = "Тестовое описание")
    String description;
}