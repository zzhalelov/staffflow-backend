package kz.zzhalelov.staffflow.server.absence.vacation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}