package kz.zzhalelov.staffflow.server.organization;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationCreateDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationMapper;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationResponseDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations", description = "Управление организациями")
@PreAuthorize("hasRole('ADMIN')")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести список всех организаций")
    public Page<OrganizationResponseDto> findAll(Pageable pageable) {
        return organizationService.findAll(pageable)
                .map(organizationMapper::toResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Поиск организации по Id")
    public OrganizationResponseDto findById(@PathVariable long id) {
        return organizationMapper.toResponse(organizationService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новую организацию")
    public OrganizationResponseDto create(@Valid @RequestBody OrganizationCreateDto dto) {
        Organization organization = organizationMapper.fromCreate(dto);
        return organizationMapper.toResponse(organizationService.create(organization));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения об организации")
    public OrganizationResponseDto update(@PathVariable long id,
                                          @Valid @RequestBody OrganizationUpdateDto dto) {
        Organization organization = organizationMapper.fromUpdate(dto);
        return organizationMapper.toResponse(organizationService.update(id, organization));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить организацию")
    public void delete(@PathVariable long id) {
        organizationService.delete(id);
    }

    @GetMapping("/bin/{idNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Поиск организации по БИН")
    public Organization findByIdNumber(@PathVariable String idNumber) {
        return organizationService.findByIdNumber(idNumber);
    }

    @PostMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Восстановить удаленную организацию (из архива)")
    public void restore(@PathVariable long id) {
        organizationService.restore(id);
    }
}