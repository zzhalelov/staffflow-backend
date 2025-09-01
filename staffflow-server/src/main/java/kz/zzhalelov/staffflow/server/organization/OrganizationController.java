package kz.zzhalelov.staffflow.server.organization;

import kz.zzhalelov.staffflow.server.organization.dto.OrganizationCreateDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationMapper;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationResponseDto;
import kz.zzhalelov.staffflow.server.organization.dto.OrganizationUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @GetMapping
    public List<OrganizationResponseDto> findAll() {
        return organizationService.findAll()
                .stream()
                .map(organizationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{organizationId}")
    public OrganizationResponseDto findById(@PathVariable long organizationId) {
        return organizationMapper.toResponse(organizationService.findById(organizationId));
    }

    @PostMapping
    public OrganizationResponseDto create(@RequestBody OrganizationCreateDto organizationCreateDto) {
        Organization organization = organizationMapper.fromCreate(organizationCreateDto);
        return organizationMapper.toResponse(organizationService.create(organization));
    }

    @PatchMapping("/{organizationId}")
    public OrganizationResponseDto update(@PathVariable long organizationId,
                                          @RequestBody OrganizationUpdateDto organizationUpdateDto) {
        Organization organization = organizationMapper.fromUpdate(organizationUpdateDto);
        return organizationMapper.toResponse(organizationService.update(organizationId, organization));
    }

    @DeleteMapping("/{organizationId}")
    public void delete(@PathVariable long organizationId) {
        organizationService.delete(organizationId);
    }

    @GetMapping("/idNumber")
    public Organization findByIdNumber(@PathVariable String idNumber) {
        return organizationService.findByIdNumber(idNumber);
    }
}