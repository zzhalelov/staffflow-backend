package kz.zzhalelov.staffflow.server.position.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionResponseDto {
    Long id;
    String name;
    double salary;
}