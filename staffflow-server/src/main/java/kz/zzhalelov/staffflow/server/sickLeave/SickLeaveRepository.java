package kz.zzhalelov.staffflow.server.sickLeave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {

    @Query("""
            select count (s)>0
            from SickLeave s
            where s.employee.id = :employeeId
            and s.startDate <= :endDate
            and s.endDate >= :startDate
            """)
    boolean existsOverlappingSickLeaves(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}