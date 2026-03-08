package kz.zzhalelov.staffflow.server.identityDocument.dto;

import kz.zzhalelov.staffflow.server.identityDocument.IdentityDocumentType;
import kz.zzhalelov.staffflow.server.person.Person;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdentityDocumentResponseDto {
    Long id;
    IdentityDocumentType idType;
    String number;
    String issuedBy;
    LocalDate issuedDate;
    LocalDate validUntil;
    Person person;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    boolean deleted;
    LocalDateTime deletedAt;
    String deletedBy;
}