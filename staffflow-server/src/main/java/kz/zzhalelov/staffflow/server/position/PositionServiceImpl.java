package kz.zzhalelov.staffflow.server.position;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    @Override
    public Position createPosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public Position addPositionIntoStaffSchedule(Long positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new NotFoundException("Position not found"));
        return null;
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
    public Position update(long positionId, Position updatedPosition) {
        Optional<Position> optionalPosition = positionRepository.findById(positionId);
        if (optionalPosition.isPresent()) {
            Position position = optionalPosition.get();
        }
        Position existingPosition = positionRepository.findById(positionId).orElseThrow();
        merge(existingPosition, updatedPosition);
        return positionRepository.save(existingPosition);
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