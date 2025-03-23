package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.Position;
import kz.zzhalelov.hrmsspringjpapractice.repository.PositionRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    private final PositionRepository positionRepository;
    private final PositionService positionService;

    //create
    @PostMapping
    public Position create(@RequestBody Position position) {
        return positionService.create(position);
    }

    @GetMapping
    public List<Position> findAll() {
        return positionService.findAll();
    }

    @GetMapping("/{id}")
    public Position findById(@PathVariable int id) {
        return positionService.findById(id);
    }

    @PutMapping("/{id}")
    public Position update(@PathVariable int id,
                           @RequestBody Position position) {
        Position existingPosition = positionRepository.findById(id).orElseThrow();
        existingPosition.setName(position.getName());
        existingPosition.setSalary(position.getSalary());
        return positionService.update(position);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        positionService.delete(id);
    }
}