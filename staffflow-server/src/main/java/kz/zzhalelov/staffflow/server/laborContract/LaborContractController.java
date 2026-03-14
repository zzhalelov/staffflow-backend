package kz.zzhalelov.staffflow.server.laborContract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractCreateDto;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractMapper;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractResponseDto;
import kz.zzhalelov.staffflow.server.laborContract.dto.LaborContractUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts")
@Tag(name = "Labor Contracts", description = "Управление трудовыми договорами сотрудников")
public class LaborContractController {
    private final LaborContractService laborContractService;
    private final LaborContractMapper laborContractMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового трудового договора")
    public LaborContractResponseDto create(@RequestParam Long organizationId,
                                           @RequestParam Long departmentId,
                                           @RequestParam Long employeeId,
                                           @RequestParam Long positionId,
                                           @Valid @RequestBody LaborContractCreateDto dto) {
        LaborContract contract = laborContractMapper.fromCreate(dto);
        return laborContractMapper.toResponse(laborContractService.create(organizationId, departmentId, employeeId, positionId, contract));
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