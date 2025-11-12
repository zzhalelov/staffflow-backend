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
    public Vacation create(Vacation vacation, long employeeId, Month month, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        vacation.setEmployee(employee);
        vacation.setStartDate(startDate);
        vacation.setEndDate(endDate);
        vacation.setMonth(month);
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
    public Vacation update(long id, Vacation vacation) {
        return null;
    }
}