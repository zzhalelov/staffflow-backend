package kz.zzhalelov.staffflow.server.organization.dto;

import kz.zzhalelov.staffflow.server.organization.OrganizationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationResponseDto {
    Long id;
    OrganizationType organizationType;
    String fullName;
    String shortName;
    String idNumber;
    Boolean hasBranches;
    Boolean isBranch;
    String address;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    boolean deleted;
    LocalDateTime deletedAt;
    String deletedBy;
}