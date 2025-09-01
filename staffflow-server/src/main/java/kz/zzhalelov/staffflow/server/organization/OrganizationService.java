package kz.zzhalelov.staffflow.server.organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    Organization create(Organization organization);

    List<Organization> findAll();

    Organization findById(long organizationId);

    Organization update(long organizationId, Organization updatedOrganization);

    void delete(long organizationId);

    Organization findByIdNumber(String idNumber);
}