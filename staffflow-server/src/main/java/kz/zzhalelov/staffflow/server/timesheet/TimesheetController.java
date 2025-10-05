package kz.zzhalelov.staffflow.server.timesheet;

import kz.zzhalelov.staffflow.server.timesheet.dto.TimesheetMapper;
import kz.zzhalelov.staffflow.server.timesheet.dto.TimesheetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/timesheets")
@RequiredArgsConstructor
public class TimesheetController {
    private final TimesheetService timesheetService;
    private final TimesheetMapper timesheetMapper;

    //create new timesheet
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimesheetResponseDto createTimesheet(@RequestParam Long organizationId,
                                                @RequestParam Month month,
                                                @RequestParam int year) {
//        Timesheet timesheet = timesheetMapper.fromCreate()
        return timesheetMapper.toResponseDto(timesheetService.createTimesheet(organizationId, month, year));
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
    public List<TimesheetResponseDto> findAll() {
        return timesheetService.findAll()
                .stream()
                .map(timesheetMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TimesheetResponseDto findById(@PathVariable Long id) {
        return timesheetMapper.toResponseDto(timesheetService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        timesheetService.deleteById(id);
    }
}