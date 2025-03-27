package kz.zzhalelov.hrmsspringjpapractice.dto.positionDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionUpdateDto {
    String name;
    double salary;
}