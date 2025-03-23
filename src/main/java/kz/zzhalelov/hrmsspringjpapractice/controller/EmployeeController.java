package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.*;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    //find all employees
    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    //find by id
    @GetMapping("/{id}")
    public Employee findById(@PathVariable int id) {
        return employeeService.findById(id);
    }

    //create
    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    //update
    @PutMapping("/{id}")
    public Employee update(@PathVariable int id,
                           @RequestBody Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow();
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        return employeeService.update(existingEmployee);
    }

    //delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        employeeService.delete(id);
    }

    //find by first name
    @GetMapping("/find-by-firstname")
    public List<Employee> findByFirstName(String name) {
        return employeeService.findByFirstName(name);
    }
}