package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.dto.departmentDto.DepartmentResponseDto;
import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import kz.zzhalelov.hrmsspringjpapractice.dto.departmentDto.DepartmentMapper;
import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import kz.zzhalelov.hrmsspringjpapractice.repository.DepartmentRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public DepartmentResponseDto create(@PathVariable int employeeId,
                                        @RequestBody Department department) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        department.setManager(employee);
        return departmentMapper.toResponse(departmentService.create(department));
    }

    //find all
    @GetMapping
    public List<DepartmentResponseDto> findAll() {
        return departmentService.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    //find by id
    @GetMapping("/{id}")
    public DepartmentResponseDto findById(@PathVariable int id) {
        return departmentMapper.toResponse(departmentService.findById(id));
    }

    //update
    @PutMapping
    public DepartmentResponseDto update(@RequestParam int departmentId,
                                        @RequestParam int employeeId,
                                        @RequestBody Department department) {
        Department existingDepartment = departmentRepository.findById(departmentId).orElseThrow();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        existingDepartment.setName(department.getName());
        existingDepartment.setManager(employee);
        return departmentMapper.toResponse(departmentService.update(existingDepartment));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        departmentService.delete(id);
    }
}