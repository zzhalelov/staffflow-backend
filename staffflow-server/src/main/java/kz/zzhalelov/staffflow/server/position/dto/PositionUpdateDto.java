package kz.zzhalelov.staffflow.server.position.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionUpdateDto {
    String name;
    List<ScheduleItemDto> scheduleItems;
}