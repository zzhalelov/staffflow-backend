package kz.zzhalelov.staffflow.server.service;

import kz.zzhalelov.staffflow.server.model.Position;

import java.util.List;

public interface PositionService {
    Position create(Position position);

    List<Position> findAll();

    Position findById(long positionId);

    Position update(long positionId, Position position);

    void delete(long positionId);
}