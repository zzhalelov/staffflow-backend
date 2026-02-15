package kz.zzhalelov.staffflow.server.department.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponseDto {
    Long id;
    String name;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}