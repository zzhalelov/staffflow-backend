package kz.zzhalelov.staffflow.server.payroll.dto;

import kz.zzhalelov.staffflow.server.organization.dto.OrganizationResponseDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayrollResponseDto {
    Long id;
    OrganizationShortResponseDto organization;
    Month month;
    int year;
    LocalDate createdAt;
    List<PayrollEntryResponseDto> entries;
}