package kz.zzhalelov.staffflow.server.controller;

import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeCreateDto;
import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeMapper;
import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeResponseDto;
import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeUpdateDto;
import kz.zzhalelov.staffflow.server.model.*;
import kz.zzhalelov.staffflow.server.repository.EmployeeRepository;
import kz.zzhalelov.staffflow.server.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    //find all employees
    @GetMapping
    public List<EmployeeResponseDto> findAll() {
        return employeeService.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    //find by id
    @GetMapping("/{id}")
    public EmployeeResponseDto findById(@PathVariable long id) {
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    //create
    @PostMapping()
    public EmployeeResponseDto create(@RequestBody EmployeeCreateDto employeeCreateDto) {
        Employee employee = employeeMapper.fromCreate(employeeCreateDto);
        return employeeMapper.toResponse(employeeService.create(employee));
    }

    //update
    @PutMapping("/{id}")
    public EmployeeResponseDto update(@PathVariable long id,
                                      @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow();
        existingEmployee.setFirstName(employeeUpdateDto.getFirstName());
        existingEmployee.setLastName(employeeUpdateDto.getLastName());
        existingEmployee.setEmail(employeeUpdateDto.getEmail());
        existingEmployee.setPhone(employeeUpdateDto.getPhone());
        return employeeMapper.toResponse(employeeService.update(existingEmployee));
    }

    //delete
    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable long employeeId) {
        employeeService.delete(employeeId);
    }

    //find by first name
    @GetMapping("/find-by-firstname")
    public List<EmployeeResponseDto> findByFirstName(String name) {
        return employeeService.findByFirstName(name)
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }
}