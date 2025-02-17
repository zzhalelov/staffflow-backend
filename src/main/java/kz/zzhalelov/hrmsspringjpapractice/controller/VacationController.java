package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.Employee;
import kz.zzhalelov.hrmsspringjpapractice.model.Vacation;
import kz.zzhalelov.hrmsspringjpapractice.repository.EmployeeRepository;
import kz.zzhalelov.hrmsspringjpapractice.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{employeeId}")
    public Vacation create(@PathVariable int employeeId,
                           @RequestBody Vacation vacation) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        vacation.setEmployee(employee);
        return vacationRepository.save(vacation);
    }
}