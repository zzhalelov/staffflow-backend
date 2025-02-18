package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.exception.NotFoundException;
import kz.zzhalelov.hrmsspringjpapractice.model.Absence;
import kz.zzhalelov.hrmsspringjpapractice.repository.AbsenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/absences")
public class AbsenceController {
    private final AbsenceRepository absenceRepository;

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
}