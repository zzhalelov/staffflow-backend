package kz.zzhalelov.staffflow.server.department;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentCreateDto;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentResponseDto;
import kz.zzhalelov.staffflow.server.department.dto.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
@Tag(name = "Departments", description = "Управление подразделениями организации")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final DepartmentService departmentService;

    //create department
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новое подразделение")
    public DepartmentResponseDto create(@RequestBody DepartmentCreateDto dto) {
        Department department = departmentMapper.fromCreate(dto);
        return departmentMapper.toResponse(departmentService.create(department));
    }

    //find all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести список всех подразделений")
    public List<DepartmentResponseDto> findAll() {
        return departmentService.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    //find by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Найти подразделение по Id")
    public DepartmentResponseDto findById(@PathVariable long id) {
        return departmentMapper.toResponse(departmentService.findById(id));
    }

    //update
    @PatchMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения о подразделении")
    public DepartmentResponseDto update(@PathVariable long departmentId,
                                        @RequestBody Department department) {
        Department existingDepartment = departmentRepository.findById(departmentId).orElseThrow();
        existingDepartment.setName(department.getName());
        return departmentMapper.toResponse(departmentService.update(existingDepartment));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить подразделение")
    public void delete(@PathVariable long id) {
        departmentService.delete(id);
    }
}