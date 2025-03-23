package kz.zzhalelov.hrmsspringjpapractice.service.impl;

import kz.zzhalelov.hrmsspringjpapractice.model.Position;
import kz.zzhalelov.hrmsspringjpapractice.repository.PositionRepository;
import kz.zzhalelov.hrmsspringjpapractice.service.PositionService;
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
    public Position findById(int positionId) {
        return positionRepository.findById(positionId).orElseThrow();
    }

    @Override
    public Position update(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public void delete(int positionId) {
        positionRepository.deleteById(positionId);
    }
}