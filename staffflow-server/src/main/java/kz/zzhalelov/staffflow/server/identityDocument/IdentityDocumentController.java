package kz.zzhalelov.staffflow.server.identityDocument;

import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentityDocumentCreateDto;
import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentityDocumentMapper;
import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentityDocumentResponseDto;
import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentutyDocumentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/identityDocuments")
@RequiredArgsConstructor
public class IdentityDocumentController {
    private final IdentityDocumentService service;
    private final IdentityDocumentMapper mapper;

    @PostMapping
    public IdentityDocumentResponseDto create(@RequestBody IdentityDocumentCreateDto dto) {
        IdentityDocument document = mapper.fromCreate(dto);
        return mapper.toResponse(service.create(document));
    }

    @GetMapping
    public List<IdentityDocumentResponseDto> findAll() {
        return service.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public IdentityDocumentResponseDto findById(@PathVariable long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PatchMapping("/{id}")
    public IdentityDocumentResponseDto update(@PathVariable long id,
                                              @RequestBody IdentutyDocumentUpdateDto dto) {
        IdentityDocument document = mapper.fromUpdate(dto);
        return mapper.toResponse(service.update(id, document));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}