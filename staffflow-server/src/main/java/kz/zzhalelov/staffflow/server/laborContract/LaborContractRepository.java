package kz.zzhalelov.staffflow.server.laborContract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LaborContractRepository extends JpaRepository<LaborContract, Long> {
    @Query("select lc from LaborContract lc where lc.employee.id = :employeeId and lc.status = 'HIRED'")
    Optional<LaborContract> finActiveByEmployeeId(@Param("employeeId") Long employeeId);
}