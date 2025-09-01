package kz.zzhalelov.staffflow.server.organization.dto;

import kz.zzhalelov.staffflow.server.organization.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public Organization fromCreate(OrganizationCreateDto dto) {
        Organization organization = new Organization();
        organization.setOrganizationType(dto.getOrganizationType());
        organization.setIdNumber(dto.getIdNumber());
        organization.setFullName(dto.getFullName());
        organization.setShortName(dto.getShortName());
        organization.setHasBranches(dto.getHasBranches());
        organization.setIsBranch(dto.getIsBranch());
        organization.setAddress(dto.getAddress());
        return organization;
    }

    public Organization fromUpdate(OrganizationUpdateDto dto) {
        Organization organization = new Organization();
        organization.setOrganizationType(dto.getOrganizationType());
        organization.setIdNumber(dto.getIdNumber());
        organization.setFullName(dto.getFullName());
        organization.setShortName(dto.getShortName());
        organization.setHasBranches(dto.getHasBranches());
        organization.setIsBranch(dto.getIsBranch());
        organization.setAddress(dto.getAddress());
        return organization;
    }

    public OrganizationResponseDto toResponse(Organization organization) {
        OrganizationResponseDto dto = new OrganizationResponseDto();
        dto.setId(organization.getId());
        dto.setOrganizationType(organization.getOrganizationType());
        dto.setIdNumber(organization.getIdNumber());
        dto.setHasBranches(organization.getHasBranches());
        dto.setIsBranch(organization.getIsBranch());
        dto.setFullName(organization.getFullName());
        dto.setShortName(organization.getShortName());
        dto.setAddress(organization.getAddress());
        return dto;
    }
}