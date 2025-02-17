package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import kz.zzhalelov.hrmsspringjpapractice.model.Vacation;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacations")
public class VacationController {
    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public List<Vacation> findAll() {
        return vacationRepository.findAll();
    }

    @GetMapping("/by-employee/{id}")
    public List<Vacation> findByEmployeeId(@PathVariable int id) {
        return vacationRepository.findByEmployee_Id(id);
    }

    @GetMapping("/by-status")
    public List<Vacation> findByStatus(@RequestParam String status) {
        return vacationRepository.findByStatus(status);
    }

    @GetMapping("/between")
    public List<Vacation> findBetweenDates(@RequestParam LocalDate start,
                                           @RequestParam LocalDate end) {
        return vacationRepository.findByStartDateAfterAndEndDateBefore(start, end);
    }

    @PostMapping("/{employeeId}")
    public Vacation create(@PathVariable int employeeId,
                           @RequestBody Vacation vacation) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        vacation.setEmployee(employee);
        return vacationRepository.save(vacation);
    }

    @PutMapping("/{id}/approve")
    public Vacation approve(@PathVariable int id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow();
        vacation.setStatus("одобрено");
        return vacationRepository.save(vacation);
    }

    @PutMapping("/{id}/reject")
    public Vacation reject(@PathVariable int id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow();
        vacation.setStatus("отклонено");
        return vacationRepository.save(vacation);
    }
}