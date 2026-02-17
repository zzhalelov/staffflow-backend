package kz.zzhalelov.staffflow.server.department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {
    Department create(Department department);

    Page<Department> findAll(Pageable pageable);

    Department findById(long departmentId);

    Department update(long id, Department department);

    void delete(long departmentId);

    void restore(long departmentId);
}