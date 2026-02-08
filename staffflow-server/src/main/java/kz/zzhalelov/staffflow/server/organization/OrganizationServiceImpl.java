package kz.zzhalelov.staffflow.server.organization;

import kz.zzhalelov.staffflow.server.exception.BadRequestException;
import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    public Organization create(Organization organization) {
        organizationRepository.findByIdNumber(organization.getIdNumber())
                .ifPresent(o -> {
                    throw new ConflictException("Организация с таким БИН уже существует: " + organization.getIdNumber());
                });
        if (organization.getIdNumber() == null
                || organization.getIdNumber().isBlank()
                || organization.getIdNumber().length() != 12) {
            throw new BadRequestException("ИИН должен быть заполнен и составлять 12 знаков");
        }
        return organizationRepository.save(organization);
    }

    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization findById(long organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Организация не найдена"));
    }

    @Override
    public Organization update(long organizationId, Organization updatedOrganization) {
        Organization existingOrganization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
        merge(existingOrganization, updatedOrganization);
        return organizationRepository.save(existingOrganization);
    }

    @Override
    public void delete(long organizationId) {
        if (!organizationRepository.existsById(organizationId)) {
            throw new NotFoundException("Организация не найдена");
        }
        organizationRepository.deleteById(organizationId);
    }

    @Override
    public Organization findByIdNumber(String idNumber) {
        return organizationRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new NotFoundException("Организация не найдена"));
    }

    private void merge(Organization existingOrganization, Organization updatedOrganization) {
        if (updatedOrganization.getIdNumber() != null && !updatedOrganization.getIdNumber().isBlank()) {
            existingOrganization.setIdNumber(updatedOrganization.getIdNumber());
        }
        if (updatedOrganization.getOrganizationType() != null) {
            existingOrganization.setOrganizationType(updatedOrganization.getOrganizationType());
        }
        if (updatedOrganization.getFullName() != null && !updatedOrganization.getFullName().isBlank()) {
            existingOrganization.setFullName(updatedOrganization.getFullName());
        }
        if (updatedOrganization.getShortName() != null && !updatedOrganization.getShortName().isBlank()) {
            existingOrganization.setShortName(updatedOrganization.getShortName());
        }
        if (updatedOrganization.getHasBranches() != null) {
            existingOrganization.setHasBranches(updatedOrganization.getHasBranches());
        }
        if (updatedOrganization.getIsBranch() != null) {
            existingOrganization.setIsBranch(updatedOrganization.getIsBranch());
        }
        if (updatedOrganization.getAddress() != null && !updatedOrganization.getAddress().isBlank()) {
            existingOrganization.setAddress(updatedOrganization.getAddress());
        }
    }
}