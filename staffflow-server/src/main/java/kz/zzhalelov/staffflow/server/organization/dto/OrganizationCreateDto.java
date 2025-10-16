package kz.zzhalelov.staffflow.server.organization.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import kz.zzhalelov.staffflow.server.organization.OrganizationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationCreateDto {
    @Enumerated(EnumType.STRING)
    OrganizationType organizationType;
    @NotBlank(message = "Full name is required")
    String fullName;
    @NotBlank(message = "Short name is required")
    String shortName;
    @NotBlank(message = "BIN is required")
    String idNumber;
    Boolean hasBranches;
    Boolean isBranch;
    @NotBlank(message = "Address is required")
    String address;
}