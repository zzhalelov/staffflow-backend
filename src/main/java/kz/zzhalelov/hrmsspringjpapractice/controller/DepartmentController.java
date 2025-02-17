package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import kz.zzhalelov.hrmsspringjpapractice.dto.DepartmentDto;
import kz.zzhalelov.hrmsspringjpapractice.dto.DepartmentMapper;
import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import kz.zzhalelov.hrmsspringjpapractice.repository.DepartmentRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;

    //create department
    @PostMapping
    public Department create(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    //find all
    @GetMapping
    public List<DepartmentDto> findAll() {
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toDto(departments);
    }

    //find by id
    @GetMapping("/{id}")
    public DepartmentDto findById(@PathVariable int id) {
        Department department = departmentRepository.findById(id).orElseThrow();
        return departmentMapper.toDto(department);
    }

    //update
    @PutMapping
    public Department update(@RequestParam int departmentId,
                             @RequestParam int employeeId,
                             @RequestBody Department department) {
        Department existingDepartment = departmentRepository.findById(departmentId).orElseThrow();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        existingDepartment.setName(department.getName());
        existingDepartment.setManager(employee);
        return departmentRepository.save(existingDepartment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        departmentRepository.deleteById(id);
    }
}