package kz.zzhalelov.staffflow.server.position;

import java.util.List;

public interface PositionService {
    Position createPosition(Position position);

    Position addPositionIntoStaffSchedule(Long positionId);

    List<Position> findAll();

    Position findById(long positionId);

    Position update(long positionId, Position position);

    void delete(long positionId);
}