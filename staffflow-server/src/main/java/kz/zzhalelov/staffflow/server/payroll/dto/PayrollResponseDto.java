package kz.zzhalelov.staffflow.server.payroll.dto;

import kz.zzhalelov.staffflow.server.organization.dto.OrganizationResponseDto;
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
    OrganizationResponseDto organization;
    Month month;
    int year;
    LocalDate createdAt;
    List<PayrollEntryResponseDto> entries;
}