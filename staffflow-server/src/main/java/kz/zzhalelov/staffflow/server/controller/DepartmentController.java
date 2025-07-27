package kz.zzhalelov.staffflow.server.controller;

import kz.zzhalelov.staffflow.server.dto.departmentDto.DepartmentCreateDto;
import kz.zzhalelov.staffflow.server.dto.departmentDto.DepartmentResponseDto;
import kz.zzhalelov.staffflow.server.model.Department;
import kz.zzhalelov.staffflow.server.dto.departmentDto.DepartmentMapper;
import kz.zzhalelov.staffflow.server.model.Employee;
import kz.zzhalelov.staffflow.server.repository.DepartmentRepository;
import kz.zzhalelov.staffflow.server.repository.EmployeeRepository;
import kz.zzhalelov.staffflow.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments/api")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;
    private final DepartmentService departmentService;

    //create department
    @PostMapping("/create")
    public DepartmentResponseDto create(@RequestBody DepartmentCreateDto dto) {
        Department department = departmentMapper.fromCreate(dto);
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
    public DepartmentResponseDto findById(@PathVariable long id) {
        return departmentMapper.toResponse(departmentService.findById(id));
    }

    //update
    @PutMapping
    public DepartmentResponseDto update(@RequestParam long departmentId,
                                        @RequestParam long employeeId,
                                        @RequestBody Department department) {
        Department existingDepartment = departmentRepository.findById(departmentId).orElseThrow();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        existingDepartment.setName(department.getName());
        existingDepartment.setManager(employee);
        return departmentMapper.toResponse(departmentService.update(existingDepartment));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        departmentService.delete(id);
    }
}