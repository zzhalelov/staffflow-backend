package kz.zzhalelov.staffflow.server.timesheet;

import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TimesheetServiceImpl implements TimesheetService {
    private final TimesheetRepository timesheetRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Timesheet createTimesheet(Long organizationId, Month month, int year) {
        Timesheet timesheet = new Timesheet();
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
        timesheet.setOrganization(organization);
        timesheet.setMonth(month);
        timesheet.setYear(year);
        timesheet.setCreatedAt(LocalDateTime.now());
        return timesheetRepository.save(timesheet);
    }

    @Override
    public Timesheet addEmployeeToTimesheet(Long timesheetId, Long employeeId) {
        Timesheet timesheet = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new NotFoundException("Timesheet not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        TimesheetEntry entry = new TimesheetEntry();
        entry.setEmployee(employee);
        entry.setTimesheet(timesheet);
        timesheet.getEntries().add(entry);
        return timesheetRepository.save(timesheet);
    }

    @Override
    public Timesheet addDayStatus(Long timesheetId, Long employeeId, int dayOfMonth, DayStatus dayStatus) {
        Timesheet timesheet = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new NotFoundException("Timesheet not found"));
        TimesheetEntry entry = timesheet.getEntries().stream()
                .filter(e -> e.getEmployee().getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Employee not in timesheet"));
        TimesheetDay day = new TimesheetDay();
        day.setEntry(entry);
        day.setDayOfMonth(dayOfMonth);
        day.setStatus(dayStatus);

        entry.getDays().add(day);
        return timesheetRepository.save(timesheet);
    }

    @Override
    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }

    @Override
    public Timesheet findById(Long id) {
        return timesheetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Timesheet not found"));
    }

    @Override
    public void deleteById(Long id) {
        if (!timesheetRepository.existsById(id)) {
            throw new NotFoundException("Timesheet with id " + id + " not found");
        }
        timesheetRepository.deleteById(id);
    }
}
