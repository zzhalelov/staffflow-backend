package kz.zzhalelov.staffflow.server.department;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentCreateDto;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentResponseDto;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentMapper;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
@Tag(name = "Departments", description = "Управление подразделениями организации")
public class DepartmentController {
    private final DepartmentMapper departmentMapper;
    private final DepartmentService departmentService;

    //create department
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новое подразделение")
    public DepartmentResponseDto create(@Valid @RequestBody DepartmentCreateDto dto) {
        Department department = departmentMapper.fromCreate(dto);
        return departmentMapper.toResponse(departmentService.create(department));
    }

    //find all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести список всех подразделений")
    public Page<DepartmentResponseDto> findAll(Pageable pageable) {
        return departmentService.findAll(pageable)
                .map(departmentMapper::toResponse);
    }

    //find by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Найти подразделение по Id")
    public DepartmentResponseDto findById(@PathVariable long id) {
        return departmentMapper.toResponse(departmentService.findById(id));
    }

    //update
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения о подразделении")
    public DepartmentResponseDto update(@PathVariable long id,
                                        @RequestBody DepartmentUpdateDto dto) {
        Department department = departmentMapper.fromUpdate(dto);
        return departmentMapper.toResponse(departmentService.update(id, department));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить подразделение")
    public void delete(@PathVariable long id) {
        departmentService.delete(id);
    }
}