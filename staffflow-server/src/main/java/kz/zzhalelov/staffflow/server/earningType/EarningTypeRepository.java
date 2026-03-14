package kz.zzhalelov.staffflow.server.earningType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EarningTypeRepository extends JpaRepository<EarningType, Long> {
    Optional<EarningType> findByIdAndDeletedFalse(Long id);

    Page<EarningType> findAllByDeletedFalse(Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);
}