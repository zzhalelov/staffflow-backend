package kz.zzhalelov.staffflow.gateway.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionDto {
    String name;
    double salary;
}