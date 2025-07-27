package kz.zzhalelov.staffflow.server.controller;

import kz.zzhalelov.staffflow.server.dto.laborContractDto.LaborContractMapper;
import kz.zzhalelov.staffflow.server.dto.laborContractDto.LaborContractResponseDto;
import kz.zzhalelov.staffflow.server.model.*;
import kz.zzhalelov.staffflow.server.repository.DepartmentRepository;
import kz.zzhalelov.staffflow.server.repository.EmployeeRepository;
import kz.zzhalelov.staffflow.server.repository.LaborContractRepository;
import kz.zzhalelov.staffflow.server.repository.PositionRepository;
import kz.zzhalelov.staffflow.server.service.LaborContractService;
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
    public LaborContractResponseDto create(@RequestParam long employeeId,
                                           @RequestParam long departmentId,
                                           @RequestParam long positionId,
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
    public LaborContractResponseDto findById(@PathVariable long id) {
        return laborContractMapper.toResponse(laborContractService.findById(id));
    }

    @PutMapping
    public LaborContractResponseDto update(@RequestParam long contractId,
                                           @RequestParam long employeeId,
                                           @RequestParam long departmentId,
                                           @RequestParam long positionId,
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