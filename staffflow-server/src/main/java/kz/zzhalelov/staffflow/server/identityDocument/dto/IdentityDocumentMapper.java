package kz.zzhalelov.staffflow.server.identityDocument.dto;

import kz.zzhalelov.staffflow.server.identityDocument.IdentityDocument;
import kz.zzhalelov.staffflow.server.person.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdentityDocumentMapper {

    public IdentityDocument fromCreate(IdentityDocumentCreateDto dto) {
        IdentityDocument document = new IdentityDocument();
        document.setIdType(dto.getIdType());
        document.setNumber(dto.getNumber());
        document.setIssuedDate(dto.getIssuedDate());
        document.setIssuedBy(dto.getIssuedBy());
        document.setValidUntil(dto.getValidUntil());
        Person person = new Person();
        person.setId(dto.getPersonId());
        document.setPerson(person);
        return document;
    }

    public IdentityDocument fromUpdate(IdentutyDocumentUpdateDto dto) {
        IdentityDocument document = new IdentityDocument();
        document.setIdType(dto.getIdType());
        document.setNumber(dto.getNumber());
        document.setIssuedDate(dto.getIssuedDate());
        document.setIssuedBy(dto.getIssuedBy());
        document.setValidUntil(dto.getValidUntil());
        Person person = new Person();
        person.setId(dto.getPersonId());
        document.setPerson(person);
        return document;
    }

    public IdentityDocumentResponseDto toResponse(IdentityDocument document) {
        IdentityDocumentResponseDto dto = new IdentityDocumentResponseDto();
        dto.setId(document.getId());
        dto.setIdType(document.getIdType());
        dto.setNumber(document.getNumber());
        dto.setIssuedBy(document.getIssuedBy());
        dto.setIssuedDate(document.getIssuedDate());
        dto.setValidUntil(document.getValidUntil());
        dto.setPerson(document.getPerson());

        dto.setCreatedAt(document.getCreatedAt());
        dto.setCreatedBy(document.getCreatedBy());
        dto.setUpdatedAt(document.getUpdatedAt());
        dto.setUpdatedBy(document.getUpdatedBy());
        dto.setDeleted(document.isDeleted());
        dto.setDeletedAt(document.getDeletedAt());
        dto.setDeletedBy(document.getDeletedBy());
        return dto;
    }
}