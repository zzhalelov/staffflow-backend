package kz.zzhalelov.staffflow.server.earning;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EarningTypeHistoryRepository extends JpaRepository<EarningTypeHistory, Long> {
    Optional<EarningTypeHistory> findTopByEarningTypeOrderByStartDateDesc(EarningType existingType);

    List<EarningTypeHistory> findAllByEarningTypeIdOrderByStartDateDesc(Long earningTypeId);
}