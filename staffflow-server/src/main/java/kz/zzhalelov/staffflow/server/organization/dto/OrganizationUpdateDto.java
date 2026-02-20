package kz.zzhalelov.staffflow.server.organization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kz.zzhalelov.staffflow.server.organization.OrganizationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationUpdateDto {
    Long id;
    @NotBlank(message = "Organization type os required")
    OrganizationType organizationType;
    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[A-Za-zА-Яа-я].*",
            message = "Название должно начинаться с буквы")
    String fullName;
    @NotBlank(message = "Short name is required")
    @Pattern(regexp = "^[A-Za-zА-Яа-я].*",
            message = "Название должно начинаться с буквы")
    String shortName;
    @NotBlank(message = "BIN is required")
    @Pattern(regexp = "^[0-9]{12}$",
            message = "БИН должен содержать 12 цифр")
    String idNumber;
    Boolean hasBranches;
    Boolean isBranch;
    @NotBlank(message = "Address is required")
    String address;
}