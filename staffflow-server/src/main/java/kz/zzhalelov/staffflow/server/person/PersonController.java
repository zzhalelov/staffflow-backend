package kz.zzhalelov.staffflow.server.person;

import kz.zzhalelov.staffflow.server.person.dto.PersonCreateDto;
import kz.zzhalelov.staffflow.server.person.dto.PersonMapper;
import kz.zzhalelov.staffflow.server.person.dto.PersonResponseDto;
import kz.zzhalelov.staffflow.server.person.dto.PersonUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonMapper mapper;
    private final PersonService service;

    @PostMapping
    public PersonResponseDto create(@RequestBody PersonCreateDto dto) {
        Person person = mapper.fromCreate(dto);
        return mapper.toResponse(service.create(person));
    }

    @GetMapping
    public Page<PersonResponseDto> findAll(Pageable pageable) {
        return service.findAll(pageable)
                .map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public PersonResponseDto findById(@PathVariable long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PatchMapping("/{id}")
    public PersonResponseDto update(@PathVariable long id,
                                    @RequestBody PersonUpdateDto dto) {
        Person person = mapper.fromUpdate(dto);
        return mapper.toResponse(service.update(id, person));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/restore")
    public void restore(@PathVariable long id) {
        service.restore(id);
    }
}