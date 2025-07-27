package kz.zzhalelov.staffflow.server.service;

import kz.zzhalelov.staffflow.server.model.Department;

import java.util.List;

public interface DepartmentService {
    Department create(Department department);

    List<Department> findAll();

    Department findById(long departmentId);

    Department update(Department department);

    void delete(long departmentId);
}