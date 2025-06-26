package kz.zzhalelov.staffflow.server.controller;

import kz.zzhalelov.staffflow.server.dto.positionDto.PositionCreateDto;
import kz.zzhalelov.staffflow.server.dto.positionDto.PositionMapper;
import kz.zzhalelov.staffflow.server.dto.positionDto.PositionResponseDto;
import kz.zzhalelov.staffflow.server.dto.positionDto.PositionUpdateDto;
import kz.zzhalelov.staffflow.server.model.Position;
import kz.zzhalelov.staffflow.server.repository.PositionRepository;
import kz.zzhalelov.staffflow.server.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions/api")
public class PositionController {
    private final PositionRepository positionRepository;
    private final PositionService positionService;
    private final PositionMapper positionMapper;

    //POST /positions/api
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