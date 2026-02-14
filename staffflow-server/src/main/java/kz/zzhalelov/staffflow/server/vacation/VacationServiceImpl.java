package kz.zzhalelov.staffflow.server.vacation;

import jakarta.persistence.EntityNotFoundException;
import kz.zzhalelov.staffflow.server.common.AbsenceStatus;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.exception.BadRequestException;
import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractRepository;
import kz.zzhalelov.staffflow.server.laborContract.LaborContractStatus;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final LaborContractRepository contractRepository;

    @Override
    public Vacation create(Long employeeId, Long organizationId, Vacation vacation) {
        checkContractStatus(employeeId);
        if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
            throw new BadRequestException("Дата начала не может быть позже даты окончания");
        }
        if (vacationRepository.existsOverlappingVacation(employeeId, vacation.getStartDate(), vacation.getEndDate())) {
            throw new ConflictException("Есть отпуск, который по датам пересекается с ранее зарегистрированным отпуском");
        }
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));

        vacation.setOrganization(organization);
        vacation.setEmployee(employee);
        vacation.setStatus(AbsenceStatus.NOT_APPROVED);
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
    public Vacation update(long id, Vacation updatedVacation) {
        Vacation existing = vacationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacation not found"));

        merge(existing, updatedVacation);
        return vacationRepository.save(existing);
    }

    private void merge(Vacation existingVacation, Vacation updatedVacation) {
        if (updatedVacation.getEmployee() != null) {
            existingVacation.setEmployee(updatedVacation.getEmployee());
        }
        if (updatedVacation.getOrganization() != null) {
            existingVacation.setOrganization(updatedVacation.getOrganization());
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

    private void checkContractStatus(long employeeId) {
        contractRepository.findByEmployee_IdAndStatusNot(employeeId, LaborContractStatus.HIRED)
                .ifPresent(lc -> {
                    throw new ConflictException("Трудовой договор не заключен, либо расторгнут");
                });
    }
}