package kz.zzhalelov.staffflow.server.identityDocument;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentityDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IdentityDocumentServiceImpl implements IdentityDocumentService {
    private final IdentityDocumentRepository repository;
    private final IdentityDocumentMapper mapper;

    @Override
    public IdentityDocument create(IdentityDocument document) {
        return repository.save(document);
    }

    @Override
    public List<IdentityDocument> findAll() {
        return repository.findAll();
    }

    @Override
    public IdentityDocument findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Identity document not found"));
    }

    @Override
    public IdentityDocument update(long id, IdentityDocument updated) {
        IdentityDocument existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Identity document not found"));
        merge(existing, updated);
        return repository.save(existing);
    }

    @Override
    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Identity document not found");
        } else {
            repository.deleteById(id);
        }
    }

    private void merge(IdentityDocument existing, IdentityDocument updated) {
        if (updated.getIdType() != null) {
            existing.setIdType(updated.getIdType());
        }
        if (updated.getNumber() != null && !updated.getNumber().isBlank()) {
            existing.setNumber(updated.getNumber());
        }
        if (updated.getIssuedBy() != null && !updated.getIssuedBy().isBlank()) {
            existing.setIssuedBy(updated.getIssuedBy());
        }
        if (updated.getIssuedDate() != null) {
            existing.setIssuedDate(updated.getIssuedDate());
        }
        if (updated.getValidUntil() != null) {
            existing.setValidUntil(updated.getValidUntil());
        }
    }
}