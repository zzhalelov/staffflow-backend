package kz.zzhalelov.staffflow.server.organization;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findByIdNumber(String idNumber);
}