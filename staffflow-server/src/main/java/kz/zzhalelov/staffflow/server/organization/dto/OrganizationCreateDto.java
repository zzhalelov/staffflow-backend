package kz.zzhalelov.staffflow.server.organization.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kz.zzhalelov.staffflow.server.organization.OrganizationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationCreateDto {
    @Enumerated(EnumType.STRING)
    OrganizationType organizationType;
    String fullName;
    String shortName;
    String idNumber;
    Boolean hasBranches;
    Boolean isBranch;
    String address;
}