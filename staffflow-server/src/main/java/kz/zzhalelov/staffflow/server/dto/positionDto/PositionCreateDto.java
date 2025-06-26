package kz.zzhalelov.staffflow.server.dto.positionDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionCreateDto {
    String name;
    double salary;
}