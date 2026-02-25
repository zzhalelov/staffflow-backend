package kz.zzhalelov.staffflow.server.organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {
    Organization create(Organization organization);

    Page<Organization> findAll(Pageable pageable);

    Organization findById(long organizationId);

    Organization update(long id, Organization organization);

    void delete(long organizationId);

    Organization findByIdNumber(String idNumber);

    void restore(long organizationId);
}