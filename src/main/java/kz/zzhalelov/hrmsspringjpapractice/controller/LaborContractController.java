package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.*;
import kz.zzhalelov.hrmsspringjpapractice.repository.DepartmentRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.PositionRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.LaborContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
public class LaborContractController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final LaborContractService laborContractService;

    @PostMapping
    public LaborContract create(@RequestParam int employeeId,
                                @RequestParam int departmentId,
                                @RequestParam int positionId,
                                @RequestBody LaborContract laborContract) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        Position position = positionRepository.findById(positionId).orElseThrow();
        laborContract.setEmployee(employee);
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        return laborContractService.create(laborContract);
    }

    @GetMapping
    public List<LaborContract> findAll() {
        return laborContractService.findAll();
    }
}