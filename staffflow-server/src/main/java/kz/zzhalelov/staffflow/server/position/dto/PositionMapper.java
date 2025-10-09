package kz.zzhalelov.staffflow.server.position.dto;

import kz.zzhalelov.staffflow.server.position.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {
    public Position fromCreate(PositionCreateDto positionCreateDto) {
        Position position = new Position();
        position.setName(positionCreateDto.getName());
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
        return dto;
    }
}