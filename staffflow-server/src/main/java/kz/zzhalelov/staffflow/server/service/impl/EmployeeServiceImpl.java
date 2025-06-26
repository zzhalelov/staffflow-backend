package kz.zzhalelov.staffflow.server.service.impl;

import kz.zzhalelov.staffflow.server.model.Employee;
import kz.zzhalelov.staffflow.server.repository.EmployeeRepository;
import kz.zzhalelov.staffflow.server.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow();
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findByFirstName(String name) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(name);
    }

    @Override
    public void delete(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}