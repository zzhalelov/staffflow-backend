package kz.zzhalelov.staffflow.server.earning.dto;

import kz.zzhalelov.staffflow.server.earning.EarningType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EarningTypeMapper {
    public EarningType fromCreate(EarningTypeCreateDto dto) {
        EarningType type = new EarningType();
        type.setName(dto.getName());
        type.setCode(dto.getCode());
        type.setIncludeInFot(dto.getIncludeInFot());
        type.setIncludeInAverageSalaryCalc(dto.getIncludeInAverageSalaryCalc());
        type.setIsIndexable(dto.getIsIndexable());
        type.setDescription(dto.getDescription());
        return type;
    }

    public EarningTypeResponseDto toResponse(EarningType type) {
        EarningTypeResponseDto dto = new EarningTypeResponseDto();
        dto.setId(type.getId());
        dto.setName(type.getName());
        dto.setCode(type.getCode());
        dto.setIncludeInFot(type.getIncludeInFot());
        dto.setIncludeInAverageSalaryCalc(type.getIncludeInAverageSalaryCalc());
        dto.setIsIndexable(type.getIsIndexable());
        dto.setDescription(type.getDescription());
        return dto;
    }
}