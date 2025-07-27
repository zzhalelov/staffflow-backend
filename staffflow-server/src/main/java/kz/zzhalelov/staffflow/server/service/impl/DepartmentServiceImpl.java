package kz.zzhalelov.staffflow.server.service.impl;

import kz.zzhalelov.staffflow.server.model.Department;
import kz.zzhalelov.staffflow.server.repository.DepartmentRepository;
import kz.zzhalelov.staffflow.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(long departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow();
    }

    @Override
    public Department update(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}