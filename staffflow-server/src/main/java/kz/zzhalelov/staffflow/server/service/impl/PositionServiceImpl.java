package kz.zzhalelov.staffflow.server.service.impl;

import kz.zzhalelov.staffflow.server.model.Position;
import kz.zzhalelov.staffflow.server.repository.PositionRepository;
import kz.zzhalelov.staffflow.server.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Override
    public Position create(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position findById(long positionId) {
        return positionRepository.findById(positionId).orElseThrow();
    }

    @Override
    public Position update(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public void delete(long positionId) {
        positionRepository.deleteById(positionId);
    }
}