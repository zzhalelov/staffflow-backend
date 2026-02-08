package kz.zzhalelov.staffflow.server.bonus.dto;

import kz.zzhalelov.staffflow.server.bonus.Bonus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BonusMapper {
    public Bonus fromCreate(BonusCreateDto dto) {
        Bonus bonus = new Bonus();
        bonus.setOrganization(dto.getOrganization());
        bonus.setMonth(dto.getMonth());
        bonus.setYear(dto.getYear());
        bonus.setCreatedAt(LocalDate.now());
        return bonus;
    }

    public BonusResponseDto toResponse(Bonus bonus) {
        BonusResponseDto dto = new BonusResponseDto();
        dto.setOrganization(bonus.getOrganization());
        dto.setMonth(bonus.getMonth());
        dto.setYear(bonus.getYear());
        dto.setCreatedAt(bonus.getCreatedAt());
        return dto;
    }
}