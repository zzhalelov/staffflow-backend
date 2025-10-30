package kz.zzhalelov.staffflow.server.regulatoryIndicator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.regulatoryIndicator.dto.RegulatoryIndicatorCreateDto;
import kz.zzhalelov.staffflow.server.regulatoryIndicator.dto.RegulatoryIndicatorMapper;
import kz.zzhalelov.staffflow.server.regulatoryIndicator.dto.RegulatoryIndicatorResponseDto;
import kz.zzhalelov.staffflow.server.regulatoryIndicator.dto.RegulatoryIndicatorUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/indicators")
@RequiredArgsConstructor
@Tag(name = "Indicators", description = "Управление регламентированными показателями")
public class RegulatoryIndicatorController {
    private final RegulatoryIndicatorService service;
    private final RegulatoryIndicatorRepository repository;
    private final RegulatoryIndicatorMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить регламентированный показатель")
    public RegulatoryIndicatorResponseDto create(@RequestBody RegulatoryIndicatorCreateDto dto) {
        RegulatoryIndicator indicator = mapper.fromCreate(dto);
        RegulatoryIndicator saved = repository.save(indicator);
        return mapper.toResponse(saved);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить список регламентированных показателей")
    public List<RegulatoryIndicatorResponseDto> findAll() {
        return service.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Найти показатель по ID")
    public RegulatoryIndicatorResponseDto findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить регламентированные показатель")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PatchMapping("/{indicatorId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить регламентированный показатель")
    public RegulatoryIndicatorResponseDto update(@PathVariable long indicatorId,
                                                 @RequestBody RegulatoryIndicatorUpdateDto dto) {
        RegulatoryIndicator indicator = mapper.fromUpdate(dto);
        return mapper.toResponse(service.update(indicatorId, indicator));
    }
}