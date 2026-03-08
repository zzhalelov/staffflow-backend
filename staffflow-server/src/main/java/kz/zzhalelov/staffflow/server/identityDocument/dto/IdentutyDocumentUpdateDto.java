package kz.zzhalelov.staffflow.server.identityDocument.dto;

import kz.zzhalelov.staffflow.server.identityDocument.IdentityDocumentType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdentutyDocumentUpdateDto {
    IdentityDocumentType idType;
    String number;
    String issuedBy;
    LocalDate issuedDate;
    LocalDate validUntil;
    Long personId;
}