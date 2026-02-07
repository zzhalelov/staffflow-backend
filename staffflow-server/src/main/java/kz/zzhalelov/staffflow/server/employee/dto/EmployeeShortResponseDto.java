package kz.zzhalelov.staffflow.server.employee.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeShortResponseDto {
    Long id;
    String firstName;
    String lastName;
}