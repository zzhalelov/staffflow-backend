package kz.zzhalelov.staffflow.server.organization.dto;

import kz.zzhalelov.staffflow.server.organization.OrganizationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationUpdateDto {
    Long id;
    OrganizationType organizationType;
    String fullName;
    String shortName;
    String idNumber;
    Boolean hasBranches;
    Boolean isBranch;
    String address;
}