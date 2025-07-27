package kz.zzhalelov.staffflow.server.dto.departmentDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentUpdateDto {
    String name;
    Long managerId;
}