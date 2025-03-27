package kz.zzhalelov.hrmsspringjpapractice.dto.positionDto;

import kz.zzhalelov.hrmsspringjpapractice.model.Position;
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
        PositionResponseDto positionResponseDto = new PositionResponseDto();
        positionResponseDto.setName(position.getName());
        positionResponseDto.setSalary(position.getSalary());
        return positionResponseDto;
    }
}