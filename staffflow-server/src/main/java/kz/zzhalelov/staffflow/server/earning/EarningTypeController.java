package kz.zzhalelov.staffflow.server.earning;

import kz.zzhalelov.staffflow.server.earning.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/earning-types")
public class EarningTypeController {
    private final EarningTypeService earningTypeService;
    private final EarningTypeMapper earningTypeMapper;
    private final EarningTypeHistoryMapper earningTypeHistoryMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EarningTypeResponseDto create(@RequestBody EarningTypeCreateDto dto) {
        EarningType type = earningTypeMapper.fromCreate(dto);
        EarningType saved = earningTypeService.create(type);
        return earningTypeMapper.toResponse(saved);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EarningTypeResponseDto> findAll() {
        return earningTypeService.findAll()
                .stream()
                .map(earningTypeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EarningTypeResponseDto findById(@PathVariable Long id) {
        return earningTypeMapper.toResponse(earningTypeService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        earningTypeService.delete(id);
    }

    @GetMapping("/{id}/history")
    @ResponseStatus(HttpStatus.OK)
    public List<EarningTypeHistoryResponseDto> getHistory(@PathVariable Long id) {
        return earningTypeService.findHistoryByEarningTypeId(id)
                .stream()
                .map(earningTypeHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EarningTypeResponseDto update(@PathVariable Long id,
                                         @RequestBody EarningTypeUpdateDto dto) {
        EarningType updated = earningTypeMapper.fromUpdate(dto);
        EarningType saved = earningTypeService.update(id, updated);
        return earningTypeMapper.toResponse(saved);
    }
}