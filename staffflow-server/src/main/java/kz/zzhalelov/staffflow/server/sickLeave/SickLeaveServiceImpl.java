package kz.zzhalelov.staffflow.server.sickLeave;

import kz.zzhalelov.staffflow.server.common.AbsenceStatus;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SickLeaveServiceImpl implements SickLeaveService {
    private final SickLeaveRepository sickLeaveRepository;
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    public SickLeave create(Long employeeId, Long organizationId, SickLeave sickLeave) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));

        sickLeave.setOrganization(organization);
        sickLeave.setEmployee(employee);
        sickLeave.setStatus(AbsenceStatus.NOT_APPROVED);
        return sickLeaveRepository.save(sickLeave);
    }

    @Override
    public List<SickLeave> findAll() {
        return sickLeaveRepository.findAll();
    }

    @Override
    public SickLeave findById(long id) {
        return sickLeaveRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sick Leave not found"));
    }

    @Override
    public void delete(long id) {
        if (sickLeaveRepository.existsById(id)) {
            sickLeaveRepository.deleteById(id);
        } else {
            throw new NotFoundException("Sick Leave not found");
        }
    }

    @Override
    public SickLeave update(long id, SickLeave updatedSickLeave) {
        SickLeave existing = sickLeaveRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sick Leave not found"));
        merge(existing, updatedSickLeave);
        return sickLeaveRepository.save(existing);
    }

    public void merge(SickLeave existing, SickLeave updated) {
        if (updated.getEmployee() != null) {
            existing.setEmployee(updated.getEmployee());
        }
        if (updated.getOrganization() != null) {
            existing.setOrganization(updated.getOrganization());
        }
        if (updated.getMonth() != null) {
            existing.setMonth(updated.getMonth());
        }
        if (updated.getYear() != null) {
            existing.setYear(updated.getYear());
        }
        if (updated.getStartDate() != null) {
            existing.setStartDate(updated.getStartDate());
        }
        if (updated.getEndDate() != null) {
            existing.setEndDate(updated.getEndDate());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
    }
}