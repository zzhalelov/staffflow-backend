package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.dto.laborContractDto.LaborContractMapper;
import kz.zzhalelov.hrmsspringjpapractice.dto.laborContractDto.LaborContractResponseDto;
import kz.zzhalelov.hrmsspringjpapractice.model.*;
import kz.zzhalelov.hrmsspringjpapractice.repository.DepartmentRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.LaborContractRepository;
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
    private final LaborContractMapper laborContractMapper;
    private final LaborContractRepository laborContractRepository;

    @PostMapping
    public LaborContractResponseDto create(@RequestParam int employeeId,
                                           @RequestParam int departmentId,
                                           @RequestParam int positionId,
                                           @RequestBody LaborContract laborContract) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        Position position = positionRepository.findById(positionId).orElseThrow();
        laborContract.setEmployee(employee);
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        return laborContractMapper.toResponse(laborContractService.create(laborContract));
    }

    @GetMapping
    public List<LaborContractResponseDto> findAll() {
        return laborContractService.findAll()
                .stream()
                .map(laborContractMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public LaborContractResponseDto findById(@PathVariable int id) {
        return laborContractMapper.toResponse(laborContractService.findById(id));
    }

    @PutMapping
    public LaborContractResponseDto update(@RequestParam int contractId,
                                           @RequestParam int employeeId,
                                           @RequestParam int departmentId,
                                           @RequestParam int positionId,
                                           @RequestBody LaborContract laborContract) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        Position position = positionRepository.findById(positionId).orElseThrow();

        LaborContract existingLaborContract = laborContractRepository.findById(contractId).orElseThrow();
        existingLaborContract.setEmployee(employee);
        existingLaborContract.setHireDate(laborContract.getHireDate());
        existingLaborContract.setDepartment(department);
        existingLaborContract.setPosition(position);
        existingLaborContract.setStatus(laborContract.getStatus());
        return laborContractMapper.toResponse(laborContractService.update(existingLaborContract));
    }
}