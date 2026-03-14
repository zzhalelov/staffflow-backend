package kz.zzhalelov.staffflow.server.earningType.dto;

import kz.zzhalelov.staffflow.server.earningType.EarningType;
import org.springframework.stereotype.Component;

@Component
public class EarningTypeMapper {
    public EarningType fromCreate(EarningTypeCreateDto dto) {
        EarningType type = new EarningType();
        type.setName(dto.getName());
        type.setCode(dto.getCode());
        type.setIncludeInFot(dto.getIncludeInFot());
        type.setIncludeInAverageSalaryCalc(dto.getIncludeInAverageSalaryCalc());
        type.setIndexable(dto.getIndexable());
        type.setDescription(dto.getDescription());
        return type;
    }

    public EarningType fromUpdate(EarningTypeUpdateDto dto) {
        EarningType type = new EarningType();
        type.setName(dto.getName());
        type.setCode(dto.getCode());
        type.setIncludeInFot(dto.getIncludeInFot());
        type.setIncludeInAverageSalaryCalc(dto.getIncludeInAverageSalaryCalc());
        type.setIndexable(dto.getIndexable());
        type.setDescription(dto.getDescription());
        return type;
    }

    public EarningTypeFullResponseDto toFullResponse(EarningType type) {
        EarningTypeFullResponseDto dto = new EarningTypeFullResponseDto();
        dto.setId(type.getId());
        dto.setName(type.getName());
        dto.setCode(type.getCode());
        dto.setIncludeInFot(type.getIncludeInFot());
        dto.setIncludeInAverageSalaryCalc(type.getIncludeInAverageSalaryCalc());
        dto.setIndexable(type.getIndexable());
        dto.setDescription(type.getDescription());

        dto.setCreatedAt(type.getCreatedAt());
        dto.setCreatedBy(type.getCreatedBy());
        dto.setUpdatedAt(type.getUpdatedAt());
        dto.setUpdatedBy(type.getUpdatedBy());
        dto.setDeleted(type.isDeleted());
        dto.setDeletedAt(type.getDeletedAt());
        dto.setDeletedBy(type.getDeletedBy());
        return dto;
    }

    public EarningTypeShortResponseDto toShortResponse(EarningType type) {
        EarningTypeShortResponseDto dto = new EarningTypeShortResponseDto();
        dto.setId(type.getId());
        dto.setName(type.getName());
        dto.setCode(type.getCode());
        dto.setIncludeInFot(type.getIncludeInFot());
        dto.setIncludeInAverageSalaryCalc(type.getIncludeInAverageSalaryCalc());
        dto.setIndexable(type.getIndexable());
        dto.setDescription(type.getDescription());
        return dto;
    }
}