package kz.zzhalelov.staffflow.server.payroll.dto;

import kz.zzhalelov.staffflow.server.earningType.EarningType;
import kz.zzhalelov.staffflow.server.earningType.dto.EarningTypeResponseDto;
import kz.zzhalelov.staffflow.server.employee.dto.EmployeeShortResponseDto;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationShortResponseDto;
import kz.zzhalelov.staffflow.server.payroll.Payroll;
import kz.zzhalelov.staffflow.server.payroll.PayrollDetail;
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

    public PayrollResponseDto toResponseDto(Payroll entity) {
        PayrollResponseDto dto = new PayrollResponseDto();
        dto.setId(entity.getId());
        dto.setMonth(entity.getMonth());
        dto.setYear(entity.getYear());
        dto.setCreatedAt(entity.getCreatedAt());

        Organization org = entity.getOrganization();
        if (org != null) {
            OrganizationShortResponseDto orgDto = new OrganizationShortResponseDto();
            orgDto.setId(org.getId());
            orgDto.setName(org.getShortName());
            dto.setOrganization(orgDto);
        }

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

        // Employee → только id и имя
        if (entry.getEmployee() != null) {
            EmployeeShortResponseDto employeeDto = new EmployeeShortResponseDto();
            employeeDto.setId(entry.getEmployee().getId());
            employeeDto.setFirstName(entry.getEmployee().getFirstName());
            employeeDto.setLastName(entry.getEmployee().getLastName());
            dto.setEmployee(employeeDto);
        }

        if (entry.getDetails() != null) {
            dto.setDetails(entry.getDetails().stream()
                    .map(this::toDetailResponse)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private PayrollDetailResponseDto toDetailResponse(PayrollDetail detail) {
        PayrollDetailResponseDto dto = new PayrollDetailResponseDto();
        dto.setId(detail.getId());
        dto.setAmount(detail.getAmount());

        if (detail.getEarningType() != null) {
            EarningType type = detail.getEarningType();
            EarningTypeResponseDto typeDto = new EarningTypeResponseDto();
            typeDto.setId(type.getId());
            typeDto.setName(type.getName());
            typeDto.setCode(type.getCode());
            typeDto.setIncludeInFot(type.getIncludeInFot());
            typeDto.setIncludeInAverageSalaryCalc(type.getIncludeInAverageSalaryCalc());
            typeDto.setIsIndexable(type.getIsIndexable());
            typeDto.setDescription(type.getDescription());
            dto.setEarningType(typeDto);
        }

        dto.setWorkedDays(detail.getWorkedDays());
        dto.setPlannedDays(detail.getPlannedDays());
        dto.setGrossSum(detail.getGrossSum());
        dto.setOpv(detail.getOpv());
        dto.setVosms(detail.getVosms());
        dto.setIpn(detail.getIpn());
        dto.setNetSum(detail.getNetSum());
        return dto;
    }
}