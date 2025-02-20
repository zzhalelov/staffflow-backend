package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.exception.NotFoundException;
import kz.zzhalelov.hrmsspringjpapractice.model.Absence;
import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import kz.zzhalelov.hrmsspringjpapractice.repository.AbsenceRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Absence findById(@PathVariable int id) {
        List<Absence> absences = absenceRepository.findAll();
        if (absences.isEmpty() || !absences.contains(absences.get(id))) {
            throw new NotFoundException("Element with id not found");
        }
        return absenceRepository.findById(id).orElseThrow();
    }

    @GetMapping("find-by-employee-id/{employeeId}")
    public Absence findByEmployeeId(@PathVariable int employeeId) {
        return absenceRepository.findByEmployee_Id(employeeId);
    }

    @PostMapping("/{employeeId}")
    public Absence createAbsence(@PathVariable int employeeId,
                                 @RequestBody Absence absence) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        absence.setEmployee(employee);
        return absenceRepository.save(absence);
    }
}