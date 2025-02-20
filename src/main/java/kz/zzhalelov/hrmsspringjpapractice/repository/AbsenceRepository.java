package kz.zzhalelov.hrmsspringjpapractice.repository;

import kz.zzhalelov.hrmsspringjpapractice.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
    Absence findByEmployee_Id(int employeeId);

    List<Absence> findByStartDateAfterAndEndDateBefore(LocalDate startDate, LocalDate endDate);
}