package kz.zzhalelov.staffflow.server.employee;

import kz.zzhalelov.staffflow.server.exception.BadRequestException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        if (employee.getIin() == null || employee.getIin().isBlank()) {
            throw new BadRequestException("ИИН должен быть заполнен");
        }
        if (employee.getIin().length() != 12) {
            throw new BadRequestException("Длина ИИН должна быть 12 символов");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @Override
    public Employee update(long employeeId, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
        }
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        merge(existingEmployee, updatedEmployee);
        return employeeRepository.save(existingEmployee);
    }

    @Override
    public List<Employee> findByLastNameContainingIgnoreCase(String lastName) {
        return employeeRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @Override
    public void delete(long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new NotFoundException("Сотрудник с id " + employeeId + " не найден");
        }
        employeeRepository.deleteById(employeeId);
    }

    private void merge(Employee existingEmployee, Employee updatedEmployee) {
        if (updatedEmployee.getFirstName() != null && !updatedEmployee.getFirstName().isBlank()) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
        }
        if (updatedEmployee.getLastName() != null && !updatedEmployee.getLastName().isBlank()) {
            existingEmployee.setLastName(updatedEmployee.getLastName());
        }
        if (updatedEmployee.getEmail() != null && !updatedEmployee.getEmail().isBlank()) {
            existingEmployee.setEmail(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getIin() != null && !updatedEmployee.getIin().isBlank()) {
            existingEmployee.setIin(updatedEmployee.getIin());
        }
        if (updatedEmployee.getEmail() != null && !updatedEmployee.getEmail().isBlank()) {
            existingEmployee.setEmail(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getPhone() != null && !updatedEmployee.getPhone().isBlank()) {
            existingEmployee.setPhone(updatedEmployee.getPhone());
        }
        if (updatedEmployee.getAddress() != null && !updatedEmployee.getAddress().isBlank()) {
            existingEmployee.setAddress(updatedEmployee.getAddress());
        }
        if (updatedEmployee.getCitizenship() != null && !updatedEmployee.getCitizenship().isBlank()) {
            existingEmployee.setCitizenship(updatedEmployee.getCitizenship());
        }
        if (updatedEmployee.getGender() != null && !updatedEmployee.getGender().describeConstable().isEmpty()) {
            existingEmployee.setGender(updatedEmployee.getGender());
        }
    }
}