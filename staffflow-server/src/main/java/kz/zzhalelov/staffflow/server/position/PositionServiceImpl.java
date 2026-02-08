package kz.zzhalelov.staffflow.server.position;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Override
    public Position createPosition(Position position) {
        positionRepository.findByNameIgnoreCase(position.getName())
                .ifPresent(p -> {
                    throw new ConflictException("Должность с таким названием уже существует: " + position.getName());
                });
        return positionRepository.save(position);
    }

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position findById(long positionId) {
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new NotFoundException("Position not found"));
    }

    @Override
    @Transactional
    public Position update(long positionId, Position updatedPosition) {
        Position existing = positionRepository.findById(positionId)
                .orElseThrow(() -> new NotFoundException("Position not found"));

        // имя
        if (updatedPosition.getName() != null && !updatedPosition.getName().isBlank()) {
            existing.setName(updatedPosition.getName());
        }

        // 🔥 начисления
        if (updatedPosition.getEntities() != null) {
            // полностью пересобираем начисления
            existing.getEntities().clear();

            updatedPosition.getEntities().forEach(schedule -> {
                schedule.setPosition(existing);
                existing.getEntities().add(schedule);
            });
        }

        return positionRepository.save(existing);
    }

    @Override
    public void delete(long positionId) {
        if (positionRepository.findById(positionId).isPresent()) {
            positionRepository.deleteById(positionId);
        } else {
            throw new NotFoundException("Position doesn't exist");
        }
    }

    private void merge(Position existingPosition, Position updatedPosition) {
        if (updatedPosition.getName() != null && !updatedPosition.getName().isBlank()) {
            existingPosition.setName(updatedPosition.getName());
        }
        positionRepository.save(existingPosition);
    }
}