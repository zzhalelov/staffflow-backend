package kz.zzhalelov.staffflow.server.timesheet;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/api/timesheets")
@RequiredArgsConstructor
public class TimesheetController {
    private final TimesheetService timesheetService;

    //create new timesheet
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Timesheet createTimesheet(@RequestParam Long organizationId,
                                     @RequestParam Month month,
                                     @RequestParam int year) {
        return timesheetService.createTimesheet(organizationId, month, year);
    }

    // add employee into timesheet
    @PostMapping("/{timesheetId}/employee")
    public Timesheet addEmployeeToTimesheet(@PathVariable Long timesheetId,
                                            @RequestParam Long employeeId) {
        return timesheetService.addEmployeeToTimesheet(timesheetId, employeeId);
    }

    //add day status
    @PostMapping("/{timesheetId}/employee/{employeeId}/day")
    public Timesheet addDayStatus(@PathVariable Long timesheetId,
                                  @PathVariable Long employeeId,
                                  @RequestParam int dayOfMonth,
                                  @RequestParam DayStatus status) {
        return timesheetService.addDayStatus(timesheetId, employeeId, dayOfMonth, status);
    }

    @GetMapping
    public List<Timesheet> findAll() {
        return timesheetService.findAll();
    }

    @GetMapping("/{id}")
    public Timesheet findById(@PathVariable Long id) {
        return timesheetService.findById(id);
    }
}