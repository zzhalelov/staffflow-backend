package kz.zzhalelov.staffflow.server.service.impl;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.model.Department;
import kz.zzhalelov.staffflow.server.model.Employee;
import kz.zzhalelov.staffflow.server.repository.DepartmentRepository;
import kz.zzhalelov.staffflow.server.repository.EmployeeRepository;
import kz.zzhalelov.staffflow.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Department create(Department department) {
        Long managerId = department.getManager().getId();
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException("Сотрудник не найден"));
        department.setManager(manager);
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Отдел не найден"));
    }

    @Override
    public Department update(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(long departmentId) {
        if (departmentRepository.findById(departmentId).isPresent()) {
            departmentRepository.deleteById(departmentId);
        } else {
            throw new NotFoundException("Отдел не найден");
        }
    }
}