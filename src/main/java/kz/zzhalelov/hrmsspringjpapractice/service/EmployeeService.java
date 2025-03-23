package kz.zzhalelov.hrmsspringjpapractice.service;

import kz.zzhalelov.hrmsspringjpapractice.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);

    List<Employee> findAll();

    Employee findById(int employeeId);

    Employee update(Employee employee);

    List<Employee> findByFirstName(String name);

    void delete(int employeeId);
}