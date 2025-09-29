package kz.zzhalelov.staffflow.server.timesheet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
}