package kz.zzhalelov.staffflow.server.service;

import kz.zzhalelov.staffflow.server.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);

    List<Employee> findAll();

    Employee findById(int employeeId);

    Employee update(Employee employee);

    List<Employee> findByFirstName(String name);

    void delete(int employeeId);
}