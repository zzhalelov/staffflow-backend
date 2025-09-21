package kz.zzhalelov.staffflow.server.position;

import kz.zzhalelov.staffflow.server.position.dto.PositionCreateDto;
import kz.zzhalelov.staffflow.server.position.dto.PositionMapper;
import kz.zzhalelov.staffflow.server.position.dto.PositionResponseDto;
import kz.zzhalelov.staffflow.server.position.dto.PositionUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public PositionResponseDto create(@RequestBody PositionCreateDto positionCreateDto) {
        Position position = positionMapper.fromCreate(positionCreateDto);
        return positionMapper.toResponse(positionService.create(position));
    }

    //GET /positions
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PositionResponseDto> findAll() {
        return positionService.findAll()
                .stream()
                .map(positionMapper::toResponse)
                .collect(Collectors.toList());
    }

    //GET /positions/{id}
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionResponseDto findById(@PathVariable long id) {
        return positionMapper.toResponse(positionService.findById(id));
    }

    //PATCH /api/positions/{positionId}
    @PatchMapping("/{positionId}")
    @ResponseStatus(HttpStatus.OK)
    public PositionResponseDto update(@PathVariable long positionId,
                                      @RequestBody PositionUpdateDto positionUpdateDto) {
        Position position = positionMapper.fromUpdate(positionUpdateDto);
        return positionMapper.toResponse(positionService.update(positionId, position));
    }

    //DELETE /api/positions/{positionId}
    @DeleteMapping("/{positionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long positionId) {
        positionService.delete(positionId);
    }
}