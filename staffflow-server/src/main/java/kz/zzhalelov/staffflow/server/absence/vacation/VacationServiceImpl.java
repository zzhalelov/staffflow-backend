package kz.zzhalelov.staffflow.server.absence.vacation;

import kz.zzhalelov.staffflow.server.absence.AbsenceStatus;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Vacation create(Vacation vacation,
                           long employeeId,
                           Month month,
                           int year,
                           LocalDate startDate,
                           LocalDate endDate,
                           VacationType vacationType) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        vacation.setEmployee(employee);
        vacation.setStartDate(startDate);
        vacation.setEndDate(endDate);
        vacation.setMonth(month);
        vacation.setYear(year);
        vacation.setStatus(AbsenceStatus.NOT_APPROVED);
        vacation.setDescription(vacation.getDescription());
        return vacationRepository.save(vacation);
    }

    @Override
    public List<Vacation> findAll() {
        return vacationRepository.findAll();
    }

    @Override
    public Vacation findById(long id) {
        return vacationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacation not found"));
    }

    @Override
    public void delete(long id) {
        if (vacationRepository.existsById(id)) {
            vacationRepository.deleteById(id);
        } else {
            throw new NotFoundException("Vacation not found");
        }
    }

    @Override
    public Vacation update(long vacationId, Vacation updatedVacation) {
        Vacation existingVacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new NotFoundException("Vacation not found"));
        merge(existingVacation, updatedVacation);
        return null;
    }

    private void merge(Vacation existingVacation, Vacation updatedVacation) {
        if (updatedVacation.getEmployee() != null) {
            existingVacation.setEmployee(updatedVacation.getEmployee());
        }
        if (updatedVacation.getMonth() != null) {
            existingVacation.setMonth(updatedVacation.getMonth());
        }
        if (updatedVacation.getYear() != null) {
            existingVacation.setYear(updatedVacation.getYear());
        }
        if (updatedVacation.getStartDate() != null) {
            existingVacation.setStartDate(updatedVacation.getStartDate());
        }
        if (updatedVacation.getEndDate() != null) {
            existingVacation.setEndDate(updatedVacation.getEndDate());
        }
        if (updatedVacation.getStatus() != null) {
            existingVacation.setStatus(updatedVacation.getStatus());
        }
        if (updatedVacation.getVacationType() != null) {
            existingVacation.setVacationType(updatedVacation.getVacationType());
        }
        if (updatedVacation.getDescription() != null) {
            existingVacation.setDescription(updatedVacation.getDescription());
        }
    }
}