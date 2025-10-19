package kz.zzhalelov.staffflow.server.payroll;

import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.laborContract.LaborContract;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractRepository;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
import kz.zzhalelov.staffflow.server.position.Position;
import kz.zzhalelov.staffflow.server.position.StaffSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {
    private final PayrollRepository payrollRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final LaborContractRepository laborContractRepository;

    @Override
    public Payroll createPayroll(Long organizationId, Month month, int year) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization Not Found"));

        Payroll payroll = new Payroll();
        payroll.setOrganization(organization);
        payroll.setMonth(month);
        payroll.setYear(year);
        payroll.setCreatedAt(LocalDate.now());
        return payrollRepository.save(payroll);
    }

    @Override
    public Payroll addEmployeeToPayroll(Long payrollId, Long employeeId) {
        Payroll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new NotFoundException("Payroll Not Found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee Not Found"));
        PayrollEntry entry = new PayrollEntry();
        entry.setPayroll(payroll);
        entry.setEmployee(employee);
        payroll.getEntries().add(entry);
        return payrollRepository.save(payroll);
    }

    @Transactional
    @Override
    public Payroll addDetail(Long payrollId, Long employeeId, Long workedDays, Long plannedDays) {
        Payroll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new NotFoundException("Payroll Not Found"));
        PayrollEntry entry = payroll.getEntries().stream()
                .filter(e -> e.getEmployee().getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Employee not in payroll"));

        //find current labor contract
        LaborContract contract = laborContractRepository.finActiveByEmployeeId(employeeId)
                .orElseThrow(() -> new NotFoundException("Active Labor Contract not found"));

        //get a position
        Position position = contract.getPosition();

        //staff schedule
        for (StaffSchedule schedule : position.getEntities()) {
            PayrollDetail detail = new PayrollDetail();
            detail.setPayrollEntry(entry);
            detail.setEarningType(schedule.getEarningType());
            detail.setAmount(schedule.getAmount());
            detail.setWorkedDays(workedDays);
            detail.setPlannedDays(plannedDays);

            BigDecimal grossSum = schedule.getAmount()
                    .divide(BigDecimal.valueOf(plannedDays), 2, RoundingMode.HALF_EVEN)
                    .multiply(BigDecimal.valueOf(workedDays));
            detail.setGrossSum(grossSum);
            entry.getDetails().add(detail);
        }
        entry.setPayroll(payroll);
        payroll.getEntries().add(entry);

        return payrollRepository.save(payroll);
    }

    @Override
    public List<Payroll> findAll() {
        return payrollRepository.findAll();
    }

    @Override
    public void delete(Long payrollId) {
        if (!payrollRepository.existsById(payrollId)) {
            throw new NotFoundException("Payroll Not Found");
        } else {
            payrollRepository.deleteById(payrollId);
        }
    }
}