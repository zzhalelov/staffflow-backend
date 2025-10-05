package kz.zzhalelov.staffflow.server.organization.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationShortResponseDto {
    Long id;
    String name;
}