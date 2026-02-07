package kz.zzhalelov.staffflow.server.vacation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
}