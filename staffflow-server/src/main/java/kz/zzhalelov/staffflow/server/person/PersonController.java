package kz.zzhalelov.staffflow.server.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.server.exception.ErrorResponse;
import kz.zzhalelov.staffflow.server.person.dto.PersonCreateDto;
import kz.zzhalelov.staffflow.server.person.dto.PersonMapper;
import kz.zzhalelov.staffflow.server.person.dto.PersonResponseDto;
import kz.zzhalelov.staffflow.server.person.dto.PersonUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Tag(name = "Persons", description = "Управление физическими лицами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public class PersonController {
    private final PersonMapper mapper;
    private final PersonService service;

    //create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать физическое лицо")
    public PersonResponseDto create(@Valid @RequestBody PersonCreateDto dto) {
        Person person = mapper.fromCreate(dto);
        return mapper.toResponse(service.create(person));
    }

    //find all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Вывести список всех физических лиц")
    public Page<PersonResponseDto> findAll(Pageable pageable) {
        return service.findAll(pageable)
                .map(mapper::toResponse);
    }

    //find by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Найти физическое лицо по Id")
    public PersonResponseDto findById(@PathVariable long id) {
        return mapper.toResponse(service.findById(id));
    }

    //update
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить сведения о физическом лице")
    public PersonResponseDto update(@PathVariable long id,
                                    @Valid @RequestBody PersonUpdateDto dto) {
        Person person = mapper.fromUpdate(dto);
        return mapper.toResponse(service.update(id, person));
    }

    //delete by id (soft delete)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить физическое лицо (в архив)")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    //restore department from archive
    @PostMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Восстановить физическое лицо (из архива)")
    public void restore(@PathVariable long id) {
        service.restore(id);
    }
}