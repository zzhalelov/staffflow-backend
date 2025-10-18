package kz.zzhalelov.staffflow.server.timesheet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Timesheets", description = "Управление табелями учета рабочего времени")
public class TimesheetController {
    private final TimesheetService timesheetService;
    private final TimesheetMapper timesheetMapper;

    //create new timesheet
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать табель учета рабочего времени")
    public TimesheetResponseDto createTimesheet(@RequestParam Long organizationId,
                                                @RequestParam Month month,
                                                @RequestParam int year) {
//        Timesheet timesheet = timesheetMapper.fromCreate()
        return timesheetMapper.toResponseDto(timesheetService.createTimesheet(organizationId, month, year));
    }

    // add employee into timesheet
    @PostMapping("/{timesheetId}/employee")
    @Operation(summary = "Добавить сотрудника в табель")
    public TimesheetResponseDto addEmployeeToTimesheet(@PathVariable Long timesheetId,
                                                       @RequestParam Long employeeId) {
        Timesheet timesheet = timesheetService.addEmployeeToTimesheet(timesheetId, employeeId);
        return timesheetMapper.toResponseDto(timesheet);
    }

    //add day status
    @PostMapping("/{timesheetId}/employee/{employeeId}/day")
    @Operation(summary = "Добавить день и его статус")
    public TimesheetResponseDto addDayStatus(@PathVariable Long timesheetId,
                                             @PathVariable Long employeeId,
                                             @RequestParam int dayOfMonth,
                                             @RequestParam DayStatus status) {
        Timesheet timesheet = timesheetService.addDayStatus(timesheetId, employeeId, dayOfMonth, status);
        return timesheetMapper.toResponseDto(timesheet);
    }

    @GetMapping
    @Operation(summary = "Вывести список всех табелей учета рабочего времени")
    public List<TimesheetResponseDto> findAll() {
        return timesheetService.findAll()
                .stream()
                .map(timesheetMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск табеля по Id")
    public TimesheetResponseDto findById(@PathVariable Long id) {
        return timesheetMapper.toResponseDto(timesheetService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить табель")
    public void deleteById(@PathVariable Long id) {
        timesheetService.deleteById(id);
    }
}