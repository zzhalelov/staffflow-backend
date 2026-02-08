package kz.zzhalelov.staffflow.server.regulatoryIndicator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegulatoryIndicatorRepository extends JpaRepository<RegulatoryIndicator, Long> {
    @Query("""
            select count(r) > 0
            from RegulatoryIndicator r
            where year(r.date) = :year
            """)
    boolean existsByYear(@Param("year") int year);
}