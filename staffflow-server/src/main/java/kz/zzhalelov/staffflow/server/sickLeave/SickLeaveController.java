package kz.zzhalelov.staffflow.server.sickLeave;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.sickLeave.dto.SickLeaveCreateDto;
import kz.zzhalelov.staffflow.server.sickLeave.dto.SickLeaveMapper;
import kz.zzhalelov.staffflow.server.sickLeave.dto.SickLeaveResponseDto;
import kz.zzhalelov.staffflow.server.sickLeave.dto.SickLeaveUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sickleaves")
@Tag(name = "SickLeaves", description = "Управление листами временной нетрудоспособности сотрудников организации")
public class SickLeaveController {
    private final SickLeaveService sickLeaveService;
    private final SickLeaveMapper sickLeaveMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новый лист временной нетрудоспособности")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создано")
    })
    public SickLeaveResponseDto create(@RequestParam Long employeeId,
                                       @RequestParam Long organizationId,
                                       @RequestBody SickLeaveCreateDto dto) {
        SickLeave sickLeave = sickLeaveMapper.fromCreate(dto);
        return sickLeaveMapper.toResponse(sickLeaveService.create(employeeId, organizationId, sickLeave));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить список листов временной нетрудоспособности",
            description = "Возвращает все листы временной нетрудоспособности в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ")
    })
    public List<SickLeaveResponseDto> findAll() {
        return sickLeaveService.findAll()
                .stream()
                .map(sickLeaveMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить лист временной нетрудоспособности по Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ")
    })
    public SickLeaveResponseDto findById(@PathVariable Long id) {
        return sickLeaveMapper.toResponse(sickLeaveService.findById(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения о листе временной нетрудоспособности")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ")
    })
    public SickLeaveResponseDto update(@PathVariable long id, @RequestBody SickLeaveUpdateDto dto) {
        SickLeave sickLeave = sickLeaveMapper.fromUpdate(dto);
        return sickLeaveMapper.toResponse(sickLeaveService.update(id, sickLeave));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить лист временной нетрудоспособности")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Нет содержимого")
    })
    public void delete(@PathVariable long id) {
        sickLeaveService.delete(id);
    }
}