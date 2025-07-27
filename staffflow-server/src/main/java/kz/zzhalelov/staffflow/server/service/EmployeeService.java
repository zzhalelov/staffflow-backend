package kz.zzhalelov.staffflow.server.service;

import kz.zzhalelov.staffflow.server.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);

    List<Employee> findAll();

    Employee findById(long employeeId);

    Employee update(Employee employee);

    List<Employee> findByFirstName(String name);

    void delete(long employeeId);
}