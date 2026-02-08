package kz.zzhalelov.staffflow.server.organization;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationCreateDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationMapper;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationResponseDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<OrganizationResponseDto> findAll() {
        return organizationService.findAll()
                .stream()
                .map(organizationMapper::toResponse)
                .collect(Collectors.toList());
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
    public OrganizationResponseDto create(@RequestBody OrganizationCreateDto organizationCreateDto) {
        Organization organization = organizationMapper.fromCreate(organizationCreateDto);
        return organizationMapper.toResponse(organizationService.create(organization));
    }

    @PatchMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения об организации")
    public OrganizationResponseDto update(@PathVariable long organizationId,
                                          @RequestBody OrganizationUpdateDto organizationUpdateDto) {
        Organization organization = organizationMapper.fromUpdate(organizationUpdateDto);
        return organizationMapper.toResponse(organizationService.update(organizationId, organization));
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить организацию")
    public void delete(@PathVariable long organizationId) {
        organizationService.delete(organizationId);
    }

    @GetMapping("/bin/{idNumber}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Поиск организации по БИН")
    public Organization findByIdNumber(@PathVariable String idNumber) {
        return organizationService.findByIdNumber(idNumber);
    }
}