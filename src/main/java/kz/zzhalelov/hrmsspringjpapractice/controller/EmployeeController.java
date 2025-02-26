package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.Department;
import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import kz.zzhalelov.hrmsspringjpapractice.model.Position;
import kz.zzhalelov.hrmsspringjpapractice.repository.DepartmentRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    //find all employees
    @GetMapping
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    //find by id
    @GetMapping("/{id}")
    public Employee findById(@PathVariable int id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    //create
    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
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
        return employeeRepository.save(existingEmployee);
    }

    //delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        employeeRepository.deleteById(id);
    }

    //find by first name
    @GetMapping("/find-by-firstname")
    public List<Employee> findByFirstName(String name) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(name);
    }
}