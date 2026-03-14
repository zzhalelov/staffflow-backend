package kz.zzhalelov.staffflow.server.earningType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zzhalelov.staffflow.server.earningType.dto.*;
import kz.zzhalelov.staffflow.server.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/earning-types")
@Tag(name = "Earning Types", description = "Управление видами начислений")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public class EarningTypeController {
    private final EarningTypeService earningTypeService;
    private final EarningTypeMapper earningTypeMapper;
    private final EarningTypeHistoryMapper earningTypeHistoryMapper;

    //create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новый вид начисления")
    public EarningTypeFullResponseDto create(@RequestBody EarningTypeCreateDto dto) {
        EarningType type = earningTypeMapper.fromCreate(dto);
        EarningType saved = earningTypeService.create(type);
        return earningTypeMapper.toFullResponse(saved);
    }

    //find all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести список всех видов начислений")
    public Page<EarningTypeFullResponseDto> findAll(Pageable pageable) {
        return earningTypeService.findAll(pageable)
                .map(earningTypeMapper::toFullResponse);
    }

    //find by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Найти вид начисления по Id")
    public EarningTypeFullResponseDto findById(@PathVariable Long id) {
        return earningTypeMapper.toFullResponse(earningTypeService.findById(id));
    }

    //delete by id (soft delete)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить вид начисления")
    public void delete(@PathVariable Long id) {
        earningTypeService.delete(id);
    }

    @GetMapping("/{id}/history")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести историю изменения вида начисления")
    public List<EarningTypeHistoryResponseDto> getHistory(@PathVariable Long id) {
        return earningTypeService.findHistoryByEarningTypeId(id)
                .stream()
                .map(earningTypeHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    //update
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения о виде начисления")
    public EarningTypeFullResponseDto update(@PathVariable Long id,
                                             @RequestBody EarningTypeUpdateDto dto) {
        EarningType type = earningTypeMapper.fromUpdate(dto);
        return earningTypeMapper.toFullResponse(earningTypeService.update(id, type));
    }

    //restore
    @PostMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Восстановить удаленный вид начисления (из архива)")
    public void restore(@PathVariable long id) {
        earningTypeService.restore(id);
    }
}