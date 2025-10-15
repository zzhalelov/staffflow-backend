package kz.zzhalelov.staffflow.server.payroll.dto;

import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationResponseDto;
import kz.zzhalelov.staffflow.server.payroll.Payroll;
import kz.zzhalelov.staffflow.server.payroll.PayrollEntry;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class PayrollMapper {
    public Payroll fromCreate(PayrollCreateDto dto) {
        Payroll payroll = new Payroll();
        payroll.setMonth(dto.getMonth());
        payroll.setYear(dto.getYear());
        payroll.setCreatedAt(LocalDate.now());
        return payroll;
    }

    public PayrollResponseDto toResponse(Payroll entity) {
        PayrollResponseDto dto = new PayrollResponseDto();
        dto.setId(entity.getId());
        Organization org = entity.getOrganization();
        if (org != null) {
            OrganizationResponseDto orgDto = new OrganizationResponseDto();
            orgDto.setId(org.getId());
            org.setShortName(org.getShortName());
            dto.setOrganization(orgDto);
        }
        dto.setMonth(entity.getMonth());
        dto.setYear(entity.getYear());
        dto.setCreatedAt(entity.getCreatedAt());

        //entries
        if (entity.getEntries() != null) {
            dto.setEntries(entity.getEntries()
                    .stream()
                    .map(this::toEntryResponse)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private PayrollEntryResponseDto toEntryResponse(PayrollEntry entry) {
        PayrollEntryResponseDto dto = new PayrollEntryResponseDto();
        dto.setId(entry.getId());
        if (entry.getPayroll() != null) {
            dto.setPayrollId(entry.getPayroll().getId());
        }
        return dto;
    }
}