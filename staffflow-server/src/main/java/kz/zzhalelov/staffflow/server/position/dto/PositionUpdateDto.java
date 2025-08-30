package kz.zzhalelov.staffflow.server.position.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionUpdateDto {
    String name;
    double salary;
}