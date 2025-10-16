package kz.zzhalelov.staffflow.server.position.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionCreateDto {
    String name;
    @JsonProperty("scheduledItems")
    List<StaffScheduleItemDto> scheduleItems;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class StaffScheduleItemDto {
        Long earningTypeId;
        BigDecimal amount;
    }
}