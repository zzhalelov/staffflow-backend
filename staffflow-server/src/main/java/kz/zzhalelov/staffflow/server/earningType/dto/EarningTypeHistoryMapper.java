package kz.zzhalelov.staffflow.server.earningType.dto;

import kz.zzhalelov.staffflow.server.earningType.EarningTypeHistory;
import org.springframework.stereotype.Component;

@Component
public class EarningTypeHistoryMapper {
    public EarningTypeHistoryResponseDto toResponse(EarningTypeHistory entity) {
        EarningTypeHistoryResponseDto dto = new EarningTypeHistoryResponseDto();
        dto.setId(entity.getId());
        dto.setEarningType(entity.getEarningType());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setIncludeInFot(entity.getIncludeInFot());
        dto.setIncludeInAverageSalaryCalc(entity.getIncludeInAverageSalaryCalc());
        dto.setIsIndexable(entity.getIsIndexable());
        dto.setComment(entity.getComment());
        return dto;
    }
}