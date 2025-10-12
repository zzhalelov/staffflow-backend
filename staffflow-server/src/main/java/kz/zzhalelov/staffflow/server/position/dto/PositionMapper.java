package kz.zzhalelov.staffflow.server.position.dto;

import kz.zzhalelov.staffflow.server.earning.EarningType;
import kz.zzhalelov.staffflow.server.earning.EarningTypeRepository;
import kz.zzhalelov.staffflow.server.position.Position;
import kz.zzhalelov.staffflow.server.position.StaffSchedule;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PositionMapper {
    private final EarningTypeRepository earningTypeRepository;

    public PositionMapper(EarningTypeRepository earningTypeRepository) {
        this.earningTypeRepository = earningTypeRepository;
    }

    public Position fromCreate(PositionCreateDto dto) {
        Position position = new Position();
        position.setName(dto.getName());

        if (dto.getScheduleItems() != null) {
            dto.getScheduleItems().forEach(item -> {
                StaffSchedule schedule = new StaffSchedule();
                EarningType earningType = earningTypeRepository.findById(item.getEarningTypeId())
                        .orElseThrow(() -> new RuntimeException("EarningType not found"));
                schedule.setEarningType(earningType);
                schedule.setAmount(item.getAmount());
                schedule.setPosition(position);

                position.getEntities().add(schedule);
            });
        }

        return position;
    }

    public Position fromUpdate(PositionUpdateDto positionUpdateDto) {
        Position position = new Position();
        position.setName(positionUpdateDto.getName());
        return position;
    }

    public PositionResponseDto toResponse(Position position) {
        PositionResponseDto dto = new PositionResponseDto();
        dto.setId(position.getId());
        dto.setName(position.getName());

        if (position.getEntities() != null) {
            dto.setScheduleItems(position.getEntities()
                    .stream()
                    .map(schedule -> {
                        PositionResponseDto.StaffScheduleDto s = new PositionResponseDto.StaffScheduleDto();
                        s.setEarningTypeId(schedule.getEarningType().getId());
                        s.setEarningTypeName(schedule.getEarningType().getName());
                        s.setAmount(schedule.getAmount());
                        return s;
                    }).collect(Collectors.toList()));
        }
        return dto;
    }
}