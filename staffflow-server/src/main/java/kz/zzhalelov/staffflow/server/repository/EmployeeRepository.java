package kz.zzhalelov.staffflow.server.repository;

import kz.zzhalelov.staffflow.server.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByLastNameContainingIgnoreCase(String lastName);
}