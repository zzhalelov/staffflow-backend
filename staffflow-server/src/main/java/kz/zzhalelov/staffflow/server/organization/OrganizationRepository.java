package kz.zzhalelov.staffflow.server.organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByIdNumber(String idNumber);

    Optional<Organization> findByFullNameIgnoreCase(String fullName);

    Optional<Organization> findByShortNameIgnoreCase(String shortName);

    Optional<Organization> findByIdAndDeletedFalse(Long id);

    Page<Organization> findAllByDeletedFalse(Pageable pageable);
}