package kz.zzhalelov.staffflow.server.timesheet;

import java.time.Month;
import java.util.List;

public interface TimesheetService {
    Timesheet createTimesheet(Long organizationId, Month month, int year);

    Timesheet addEmployeeToTimesheet(Long timesheetId, Long employeeId);

    Timesheet addDayStatus(Long timesheetId, Long employeeId, int dayOfMonth, DayStatus dayStatus);

    List<Timesheet> findAll();

    Timesheet findById(Long id);

    void deleteById(Long id);
}