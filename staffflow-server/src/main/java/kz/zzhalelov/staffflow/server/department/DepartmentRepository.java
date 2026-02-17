package kz.zzhalelov.staffflow.server.department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameIgnoreCase(String name);

    Optional<Department> findByIdAndDeletedFalse(Long id);

    Page<Department> findAllByDeletedFalse(Pageable pageable);
}