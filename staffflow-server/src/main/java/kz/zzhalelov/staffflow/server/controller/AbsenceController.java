package kz.zzhalelov.staffflow.server.controller;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.model.Absence;
import kz.zzhalelov.staffflow.server.model.AbsenceStatus;
import kz.zzhalelov.staffflow.server.model.Employee;
import kz.zzhalelov.staffflow.server.repository.AbsenceRepository;
import kz.zzhalelov.staffflow.server.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/absences")
public class AbsenceController {
    private final AbsenceRepository absenceRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public List<Absence> findAll() {
        return absenceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Absence findById(@PathVariable long id) {
        List<Absence> absences = absenceRepository.findAll();
        if (absences.isEmpty()) {
            throw new NotFoundException("Element with id not found");
        }
        return absenceRepository.findById(id).orElseThrow();
    }

    @GetMapping("find-by-employee-id/{employeeId}")
    public Absence findByEmployeeId(@PathVariable long employeeId) {
        return absenceRepository.findByEmployee_Id(employeeId);
    }

    @GetMapping("find-between-dates")
    public List<Absence> findBetweenDates(@RequestParam LocalDate startDate,
                                          @RequestParam LocalDate endDate) {
        return absenceRepository.findByStartDateAfterAndEndDateBefore(startDate, endDate);
    }

    @PostMapping("/{employeeId}")
    public Absence createAbsence(@PathVariable long employeeId,
                                 @RequestBody Absence absence) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        absence.setEmployee(employee);
        return absenceRepository.save(absence);
    }

    @PutMapping("/{absenceId}/status")
    public Absence approveOrRejectAbsence(@PathVariable long absenceId,
                                          @RequestParam String status) {
        Absence absence = absenceRepository.findById(absenceId).orElseThrow();
        if (status.equals("APPROVED")) {
            absence.setStatus(AbsenceStatus.APPROVED);
        } else if (status.equals("REJECTED")) {
            absence.setStatus(AbsenceStatus.REJECTED);
        } else {
            throw new IllegalArgumentException("Статус не найден");
        }
        return absenceRepository.save(absence);
    }

    @DeleteMapping("/{absenceId}")
    public void deleteAbsence(@PathVariable long absenceId) {
        absenceRepository.deleteById(absenceId);
    }
}