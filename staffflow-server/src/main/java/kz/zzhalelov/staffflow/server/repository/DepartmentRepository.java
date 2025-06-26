package kz.zzhalelov.staffflow.server.repository;

import kz.zzhalelov.staffflow.server.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}