package kz.zzhalelov.hrmsspringjpapractice.repository;

import kz.zzhalelov.hrmsspringjpapractice.model.Vacation;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Integer> {
    List<Vacation> findByEmployee_Id(int employeeId);

    List<Vacation> findByStatus(String status);

    List<Vacation> findByStartDateAfterAndEndDateBefore(LocalDate start, LocalDate end);
}