package kz.zzhalelov.staffflow.server.position.dto;

import kz.zzhalelov.staffflow.server.position.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {
    public Position fromCreate(PositionCreateDto positionCreateDto) {
        Position position = new Position();
        position.setName(positionCreateDto.getName());
        position.setSalary(positionCreateDto.getSalary());
        return position;
    }

    public Position fromUpdate(PositionUpdateDto positionUpdateDto) {
        Position position = new Position();
        position.setName(positionUpdateDto.getName());
        position.setSalary(positionUpdateDto.getSalary());
        return position;
    }

    public PositionResponseDto toResponse(Position position) {
        PositionResponseDto dto = new PositionResponseDto();
        dto.setId(position.getId());
        dto.setName(position.getName());
        dto.setSalary(position.getSalary());
        return dto;
    }
}