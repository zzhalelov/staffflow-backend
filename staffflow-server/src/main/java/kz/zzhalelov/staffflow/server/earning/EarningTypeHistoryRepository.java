package kz.zzhalelov.staffflow.server.earning;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EarningTypeHistoryRepository extends JpaRepository<EarningTypeHistory, Long> {
}