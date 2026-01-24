package kz.zzhalelov.staffflow.server.position;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.position.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/positions")
@Tag(name = "Positions", description = "Управление должностями и штатным расписанием")
public class PositionController {
    private final PositionService positionService;
    private final PositionMapper positionMapper;

    //POST /api/positions
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить позицию штатного расписания")
    public PositionFullResponseDto create(@RequestBody PositionCreateDto dto) {
        Position position = positionMapper.fromCreate(dto);
        Position saved = positionService.createPosition(position);

        return positionMapper.toFullResponse(saved);
    }

    //GET /positions
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести все должности")
    public List<PositionFullResponseDto> findAll() {
        return positionService.findAll()
                .stream()
                .map(positionMapper::toFullResponse)
                .collect(Collectors.toList());
    }

    //GET /positions/{id}
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Поиск должности по Id")
    public PositionFullResponseDto findById(@PathVariable long id) {
        return positionMapper.toFullResponse(positionService.findById(id));
    }

    //PATCH /api/positions/{positionId}
    @PatchMapping("/{positionId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения о позиции штатного расписания")
    public PositionFullResponseDto update(@PathVariable long positionId,
                                          @RequestBody PositionUpdateDto positionUpdateDto) {
        Position position = positionMapper.fromUpdate(positionUpdateDto);
        return positionMapper.toFullResponse(positionService.update(positionId, position));
    }

    //DELETE /api/positions/{positionId}
    @DeleteMapping("/{positionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить позицию штатного расписания")
    public void delete(@PathVariable long positionId) {
        positionService.delete(positionId);
    }
}