package kz.zzhalelov.staffflow.server.service;

import kz.zzhalelov.staffflow.server.model.Position;

import java.util.List;

public interface PositionService {
    Position create(Position position);

    List<Position> findAll();

    Position findById(int positionId);

    Position update(Position position);

    void delete(int positionId);
}