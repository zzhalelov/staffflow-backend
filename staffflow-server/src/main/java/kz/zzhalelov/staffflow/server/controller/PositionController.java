package kz.zzhalelov.staffflow.server.controller;

import kz.zzhalelov.staffflow.server.dto.positionDto.PositionCreateDto;
import kz.zzhalelov.staffflow.server.dto.positionDto.PositionMapper;
import kz.zzhalelov.staffflow.server.dto.positionDto.PositionResponseDto;
import kz.zzhalelov.staffflow.server.dto.positionDto.PositionUpdateDto;
import kz.zzhalelov.staffflow.server.model.Employee;
import kz.zzhalelov.staffflow.server.model.Position;
import kz.zzhalelov.staffflow.server.repository.PositionRepository;
import kz.zzhalelov.staffflow.server.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionRepository positionRepository;
    private final PositionService positionService;
    private final PositionMapper positionMapper;

    //POST /api/positions
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
    public PositionResponseDto findById(@PathVariable long id) {
        return positionMapper.toResponse(positionService.findById(id));
    }

    //PATCH /api/positions/{positionId}
    @PatchMapping("/{positionId}")
    public PositionResponseDto update(@PathVariable long positionId,
                                      @RequestBody PositionUpdateDto positionUpdateDto) {
        Position position = positionMapper.fromUpdate(positionUpdateDto);
        return positionMapper.toResponse(positionService.update(positionId, position));
    }

    //DELETE /api/positions/{positionId}
    @DeleteMapping("/{positionId}")
    public void delete(@PathVariable long positionId) {
        positionService.delete(positionId);
    }
}