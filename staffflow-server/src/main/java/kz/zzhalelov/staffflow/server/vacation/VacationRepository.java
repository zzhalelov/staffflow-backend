package kz.zzhalelov.staffflow.server.vacation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("""
            select count (v) >0
            from Vacation v
            where v.employee.id = :employeeId
            and v.startDate <= :endDate
            and  v.endDate >= :startDate
            """)
    boolean existsOverlappingVacation(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}