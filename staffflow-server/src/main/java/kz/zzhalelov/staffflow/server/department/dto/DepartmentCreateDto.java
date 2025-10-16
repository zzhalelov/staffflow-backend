package kz.zzhalelov.staffflow.server.department.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentCreateDto {
    @NotBlank(message = "Name is required")
    String name;
}