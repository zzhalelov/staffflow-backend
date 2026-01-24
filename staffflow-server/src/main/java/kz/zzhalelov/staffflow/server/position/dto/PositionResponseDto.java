package kz.zzhalelov.staffflow.server.position.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionResponseDto {
    Long id;
    String name;

//    List<StaffScheduleDto> scheduleItems;
//
//    @Data
//    @FieldDefaults(level = AccessLevel.PRIVATE)
//    public static class StaffScheduleDto {
//        Long earningTypeId;
//        String earningTypeName;
//        BigDecimal amount;
//    }
}