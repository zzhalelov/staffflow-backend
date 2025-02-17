package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.model.Position;
import kz.zzhalelov.hrmsspringjpapractice.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    private final PositionRepository positionRepository;

    //create
    @PostMapping
    public Position create(@RequestBody Position position) {
        return positionRepository.save(position);
    }

    @GetMapping
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Position findById(@PathVariable int id) {
        return positionRepository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Position update(@PathVariable int id,
                           @RequestBody Position position) {
        Position existingPosition = positionRepository.findById(id).orElseThrow();
        existingPosition.setName(position.getName());
        existingPosition.setSalary(position.getSalary());
        return positionRepository.save(existingPosition);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        positionRepository.deleteById(id);
    }
}