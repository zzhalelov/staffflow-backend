package kz.zzhalelov.staffflow.server.regulatoryIndicator.dto;

import kz.zzhalelov.staffflow.server.regulatoryIndicator.RegulatoryIndicator;
import org.springframework.stereotype.Component;

@Component
public class RegulatoryIndicatorMapper {

    public RegulatoryIndicator fromCreate(RegulatoryIndicatorCreateDto dto) {
        RegulatoryIndicator indicator = new RegulatoryIndicator();
        indicator.setDate(dto.getDate());
        indicator.setMzpValue(dto.getMzpValue());
        indicator.setMrpValue(dto.getMrpValue());
        indicator.setDescription(dto.getDescription());
        return indicator;
    }

    public RegulatoryIndicator fromUpdate(RegulatoryIndicatorUpdateDto dto) {
        RegulatoryIndicator indicator = new RegulatoryIndicator();
        indicator.setDate(dto.getDate());
        indicator.setMzpValue(dto.getMzpValue());
        indicator.setMrpValue(dto.getMrpValue());
        indicator.setDescription(dto.getDescription());
        return indicator;
    }

    public RegulatoryIndicatorResponseDto toResponse(RegulatoryIndicator indicator) {
        RegulatoryIndicatorResponseDto dto = new RegulatoryIndicatorResponseDto();
        dto.setId(indicator.getId());
        dto.setDate(indicator.getDate());
        dto.setMzpValue(indicator.getMzpValue());
        dto.setMrpValue(indicator.getMrpValue());
        dto.setDescription(indicator.getDescription());
        return dto;
    }
}