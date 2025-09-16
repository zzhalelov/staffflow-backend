package kz.zzhalelov.staffflow.server.employee;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);

    List<Employee> findAll(int from, int size);

    Employee findById(long employeeId);

    Employee update(long emloyeeId, Employee employee);

    void delete(long employeeId);

    List<Employee> findByLastNameContainingIgnoreCase(String lastName);
}