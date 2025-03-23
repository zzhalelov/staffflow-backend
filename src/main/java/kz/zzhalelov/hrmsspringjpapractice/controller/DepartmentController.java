package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import kz.zzhalelov.hrmsspringjpapractice.dto.DepartmentDto;
import kz.zzhalelov.hrmsspringjpapractice.dto.DepartmentMapper;
import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import kz.zzhalelov.hrmsspringjpapractice.repository.DepartmentRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.DepartmentService;
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
    private final DepartmentService departmentService;

    //create department
    @PostMapping("/{employeeId}")
    public Department create(@PathVariable int employeeId,
                             @RequestBody Department department) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        department.setManager(employee);
        return departmentService.create(department);
    }

    //find all
    @GetMapping
    public List<DepartmentDto> findAll() {
        List<Department> departments = departmentService.findAll();
        return departmentMapper.toDto(departments);
    }

    //find by id
    @GetMapping("/{id}")
    public DepartmentDto findById(@PathVariable int id) {
        Department department = departmentService.findById(id);
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
        return departmentService.update(existingDepartment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        departmentService.delete(id);
    }
}