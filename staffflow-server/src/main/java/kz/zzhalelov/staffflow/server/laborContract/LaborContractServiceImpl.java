package kz.zzhalelov.staffflow.server.laborContract;

import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.department.DepartmentRepository;
import kz.zzhalelov.staffflow.server.email.EmailService;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.event.ContractSignedEvent;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
import kz.zzhalelov.staffflow.server.position.Position;
import kz.zzhalelov.staffflow.server.position.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaborContractServiceImpl implements LaborContractService {
    private final LaborContractRepository laborContractRepository;
    private final LaborContractHistoryRepository laborContractHistoryRepository;
    private final EmailService emailService;
    private final ApplicationEventPublisher eventPublisher;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    @Override
    public LaborContract create(Long organizationId, Long departmentId, Long employeeId, Long positionId,
                                LaborContract laborContract) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found"));
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new NotFoundException("Position not found"));
        laborContract.setOrganization(organization);
        laborContract.setEmployee(employee);
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        return laborContractRepository.save(laborContract);
    }

    @Override
    public List<LaborContract> findAll() {
        return laborContractRepository.findAll();
    }

    @Override
    public LaborContract findById(long id) {
        return laborContractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));
    }

    @Override
    public void delete(long id) {
        LaborContract laborContract = findById(id);
        if (laborContract.getStatus().equals(LaborContractStatus.HIRED)) {
            throw new IllegalStateException("Contract with id " + id + " is already hired!");
        }
        if (laborContract.getStatus().equals(LaborContractStatus.RESIGNED)) {
            throw new IllegalStateException("Contract with id " + id + " is resigned!");
        }
        laborContractRepository.deleteById(id);
    }

    @Override
    @Transactional
    public LaborContract updateStatus(long id, LaborContractStatus newStatus) {
        LaborContract contract = laborContractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));

        // обновляем статус в основном договоре
        contract.setStatus(newStatus);
        laborContractRepository.save(contract);

        // добавляем запись в историю
        LaborContractHistory history = new LaborContractHistory();
        history.setLaborContract(contract);
        history.setStatus(newStatus);
        history.setChangedAt(LocalDateTime.now());
        laborContractHistoryRepository.save(history);

        //Отправка события
        if (newStatus == LaborContractStatus.HIRED && contract.getEmployee() != null) {
            String email = contract.getEmployee().getEmail();
            String name = contract.getEmployee().getFirstName();
            eventPublisher.publishEvent(new ContractSignedEvent(this, email, name, contract.getId()));
        }
        return contract;
    }

    @Override
    public LaborContract update(long id, LaborContract updatedLaborContract) {
        LaborContract existingLaborContract = laborContractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));

        // перед изменением сохраняем запись в историю
        LaborContractHistory history = new LaborContractHistory();
        history.setLaborContract(existingLaborContract);
        history.setStatus(existingLaborContract.getStatus());
        history.setDepartment(existingLaborContract.getDepartment());
        history.setPosition(existingLaborContract.getPosition());
        history.setChangedAt(LocalDateTime.now());
        laborContractHistoryRepository.save(history);

        // обновляем актуальные данные в основном договоре
        merge(existingLaborContract, updatedLaborContract);

        return laborContractRepository.save(existingLaborContract);
    }

    public void merge(LaborContract existingLaborContract, LaborContract updated) {
        if (updated.getOrganization() != null) {
            existingLaborContract.setOrganization(updated.getOrganization());
        }
        if (updated.getEmployee() != null) {
            existingLaborContract.setEmployee(updated.getEmployee());
        }
        if (updated.getDepartment() != null) {
            existingLaborContract.setDepartment(updated.getDepartment());
        }
        if (updated.getPosition() != null) {
            existingLaborContract.setPosition(updated.getPosition());
        }
        if (updated.getHireDate() != null) {
            existingLaborContract.setHireDate(updated.getHireDate());
        }
        if (updated.getStatus() != null) {
            existingLaborContract.setStatus(updated.getStatus());
        }
    }
}