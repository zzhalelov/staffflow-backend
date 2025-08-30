package kz.zzhalelov.staffflow.server.absence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    Absence findByEmployee_Id(long employeeId);

    List<Absence> findByStartDateAfterAndEndDateBefore(LocalDate startDate, LocalDate endDate);
}