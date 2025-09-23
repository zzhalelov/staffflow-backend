package kz.zzhalelov.staffflow.server.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByLastNameContainingIgnoreCase(String lastName);

    Page<Employee> findAll(Pageable pageable);

    Optional<Employee> findByIin(String iin);
}