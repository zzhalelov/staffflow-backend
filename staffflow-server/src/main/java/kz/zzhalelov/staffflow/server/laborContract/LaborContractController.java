package kz.zzhalelov.staffflow.server.laborContract;

import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractMapper;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractResponseDto;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractUpdateDto;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
import kz.zzhalelov.staffflow.server.position.Position;
import kz.zzhalelov.staffflow.server.department.DepartmentRepository;
import kz.zzhalelov.staffflow.server.employee.EmployeeRepository;
import kz.zzhalelov.staffflow.server.position.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts")
public class LaborContractController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final LaborContractService laborContractService;
    private final LaborContractMapper laborContractMapper;
    private final LaborContractRepository laborContractRepository;
    private final OrganizationRepository organizationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LaborContractResponseDto create(@RequestParam long organizationId,
                                           @RequestParam long employeeId,
                                           @RequestParam long departmentId,
                                           @RequestParam long positionId,
                                           @RequestBody LaborContract laborContract) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found"));
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new NotFoundException("Position not found"));
        laborContract.setOrganization(organization);
        laborContract.setEmployee(employee);
        laborContract.setDepartment(department);
        laborContract.setPosition(position);
        return laborContractMapper.toResponse(laborContractService.create(laborContract));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LaborContractResponseDto> findAll() {
        return laborContractService.findAll()
                .stream()
                .map(laborContractMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LaborContractResponseDto findById(@PathVariable long id) {
        return laborContractMapper.toResponse(laborContractService.findById(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LaborContractResponseDto update(@PathVariable long id,
                                           @RequestBody LaborContractUpdateDto dto) {
        LaborContract laborContract = laborContractMapper.fromUpdate(dto);
        return laborContractMapper.toResponse(laborContractService.update(id, laborContract));
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public LaborContractResponseDto updateStatus(@PathVariable long id,
                                                 @RequestParam LaborContractStatus status) {
        LaborContract updated = laborContractService.updateStatus(id, status);
        return laborContractMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        laborContractRepository.deleteById(id);
    }
}