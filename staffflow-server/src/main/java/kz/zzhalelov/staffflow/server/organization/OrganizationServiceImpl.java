package kz.zzhalelov.staffflow.server.organization;

import kz.zzhalelov.staffflow.server.exception.BadRequestException;
import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    public Organization create(Organization organization) {
        log.info("Creating organization: {}", organization.getIdNumber());
        organizationRepository.findByIdNumber(organization.getIdNumber())
                .ifPresent(o -> {
                    log.warn("Organization with idNumber {} already exists", organization.getIdNumber());
                    throw new ConflictException("Организация с таким БИН уже существует: " + organization.getIdNumber());
                });

        log.info("Creating organization; {}", organization.getFullName());
        organizationRepository.findByFullNameIgnoreCase(organization.getFullName())
                .ifPresent((o -> {
                    log.warn("Organization with full name {} already exists", organization.getFullName());
                    throw new ConflictException("Организация с таким полным наименованием уже существует: "
                            + organization.getFullName());
                }));

        log.info("Creating organization; {}", organization.getShortName());
        organizationRepository.findByShortNameIgnoreCase(organization.getShortName())
                .ifPresent((o -> {
                    log.warn("Organization with short name {} already exists", organization.getShortName());
                    throw new ConflictException("Организация с таким кратким наименованием уже существует: "
                            + organization.getShortName());
                }));

        if (organization.getIdNumber() == null
                || organization.getIdNumber().isBlank()
                || organization.getIdNumber().length() != 12) {
            log.warn("BIN should consists 12 digits");
            throw new BadRequestException("БИН должен быть заполнен и составлять 12 знаков");
        }
        Organization saved = organizationRepository.save(organization);
        log.info("Organization created id={} shortname='{}'", organization.getId(), organization.getShortName());
        return saved;
    }

    @Override
    public Page<Organization> findAll(Pageable pageable) {
        log.debug("Fetching organizations page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return organizationRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Organization findById(long organizationId) {
        log.debug("Fetching organization id={}", organizationId);
        return organizationRepository.findByIdAndDeletedFalse(organizationId)
                .orElseThrow(() -> {
                    log.warn("Organization not found id={}", organizationId);
                    return new NotFoundException("Организация не найдена");
                });
    }

    @Override
    public Organization update(long id, Organization updated) {
        log.info("Attempt to update organization id={}", id);
        Organization existing = organizationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Updating failed: organization not found id={}", id);
                    return new NotFoundException("Organization not found");
                });
        merge(existing, updated);
        Organization saved = organizationRepository.save(existing);
        log.info("Organization updated id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public void delete(long organizationId) {
        log.info("Attempt to soft delete organization id={}", organizationId);
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> {
                    log.warn("Deleting failed: organization not found id={}", organizationId);
                    return new NotFoundException("Organization not found");
                });

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        organization.markAsDeleted(username);
        log.info("Organization id={} soft deleted by {}", organizationId, username);
    }

    @Override
    public Organization findByIdNumber(String idNumber) {
        log.debug("Fetching organization idNumber={}", idNumber);
        return organizationRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> {
                    log.warn("Organization not found idNumber={}", idNumber);
                    return new NotFoundException("Организация не найдена");
                });
    }

    @Transactional
    public void restore(long id) {
        log.info("Attempt to restore organization id={}", id);
        Organization organization = organizationRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Restore failed: organization not found id={}", id);
                    return new NotFoundException("Organization not found");
                });

        if (!organization.isDeleted()) {
            log.warn("Restore failed: organization id={} is not deleted", id);
            throw new ConflictException("Организация не удалена");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        organization.restore(username);
        log.info("Organization id={} restored by {}", id, username);
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