package kz.zzhalelov.hrmsspringjpapractice.service.impl;

import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import kz.zzhalelov.hrmsspringjpapractice.repository.DepartmentRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.DepartmentService;
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
    public Department findById(int departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow();
    }

    @Override
    public Department update(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(int departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}