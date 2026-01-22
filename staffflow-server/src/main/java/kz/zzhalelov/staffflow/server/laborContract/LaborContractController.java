package kz.zzhalelov.staffflow.server.laborContract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractCreateDto;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractMapper;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractResponseDto;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractUpdateDto;
import kz.zzhalelov.staffflow.server.organization.OrganizationRepository;
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
@Tag(name = "Labor Contracts", description = "Управление трудовыми договорами сотрудников")
public class LaborContractController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final LaborContractService laborContractService;
    private final LaborContractMapper laborContractMapper;
    private final OrganizationRepository organizationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового трудового договора")
    public LaborContractResponseDto create(@RequestParam long organizationId,
                                           @RequestParam long employeeId,
                                           @RequestParam long departmentId,
                                           @RequestParam long positionId,
                                           @RequestBody LaborContractCreateDto createDto) {
        organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found"));
        positionRepository.findById(positionId)
                .orElseThrow(() -> new NotFoundException("Position not found"));
        createDto.setOrganizationId(organizationId);
        createDto.setEmployeeId(employeeId);
        createDto.setDepartmentId(departmentId);
        createDto.setPositionId(positionId);
        LaborContract contract = laborContractMapper.fromCreate(createDto);
        return laborContractMapper.toResponse(laborContractService.create(contract));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести список всех трудовых договоров")
    public List<LaborContractResponseDto> findAll() {
        return laborContractService.findAll()
                .stream()
                .map(laborContractMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Поиск трудового договора по Id")
    public LaborContractResponseDto findById(@PathVariable long id) {
        return laborContractMapper.toResponse(laborContractService.findById(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения о трудовом договоре")
    public LaborContractResponseDto update(@PathVariable long id,
                                           @RequestBody LaborContractUpdateDto dto) {
        LaborContract laborContract = laborContractMapper.fromUpdate(dto);
        return laborContractMapper.toResponse(laborContractService.update(id, laborContract));
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить статус трудового договора")
    public LaborContractResponseDto updateStatus(@PathVariable long id,
                                                 @RequestParam LaborContractStatus status) {
        LaborContract updated = laborContractService.updateStatus(id, status);
        return laborContractMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить трудовой договор")
    public void delete(@PathVariable long id) {
        laborContractService.delete(id);
    }
}