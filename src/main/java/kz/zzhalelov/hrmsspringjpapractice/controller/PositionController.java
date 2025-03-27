package kz.zzhalelov.hrmsspringjpapractice.controller;

import kz.zzhalelov.hrmsspringjpapractice.dto.positionDto.PositionCreateDto;
import kz.zzhalelov.hrmsspringjpapractice.dto.positionDto.PositionMapper;
import kz.zzhalelov.hrmsspringjpapractice.dto.positionDto.PositionResponseDto;
import kz.zzhalelov.hrmsspringjpapractice.dto.positionDto.PositionUpdateDto;
import kz.zzhalelov.hrmsspringjpapractice.model.Position;
import kz.zzhalelov.hrmsspringjpapractice.repository.PositionRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {
    private final PositionRepository positionRepository;
    private final PositionService positionService;
    private final PositionMapper positionMapper;

    //POST /positions
    @PostMapping
    public PositionResponseDto create(@RequestBody PositionCreateDto positionCreateDto) {
        Position position = positionMapper.fromCreate(positionCreateDto);
        return positionMapper.toResponse(positionService.create(position));
    }

    //GET /positions
    @GetMapping
    public List<PositionResponseDto> findAll() {
        return positionService.findAll()
                .stream()
                .map(positionMapper::toResponse)
                .collect(Collectors.toList());
    }

    //GET /positions/{id}
    @GetMapping("/{id}")
    public PositionResponseDto findById(@PathVariable int id) {
        return positionMapper.toResponse(positionService.findById(id));
    }

    //PUT /positions/{id}
    @PutMapping("/{id}")
    public PositionResponseDto update(@PathVariable int id,
                                      @RequestBody PositionUpdateDto positionUpdateDto) {
        Position existingPosition = positionRepository.findById(id).orElseThrow();
        existingPosition.setName(positionUpdateDto.getName());
        existingPosition.setSalary(positionUpdateDto.getSalary());
        return positionMapper.toResponse(positionService.update(existingPosition));
    }

    //DELETE /positions/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        positionService.delete(id);
    }
}