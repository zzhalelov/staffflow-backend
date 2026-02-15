package kz.zzhalelov.staffflow.server.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentCreateDto {
    @NotBlank(message = "Name is required")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-я].*",
            message = "Название должно начинаться с буквы"
    )
    String name;
}