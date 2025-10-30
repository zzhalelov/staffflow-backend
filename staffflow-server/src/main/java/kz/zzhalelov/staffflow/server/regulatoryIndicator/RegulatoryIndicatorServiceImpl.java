package kz.zzhalelov.staffflow.server.regulatoryIndicator;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegulatoryIndicatorServiceImpl implements RegulatoryIndicatorService {
    private final RegulatoryIndicatorRepository repository;

    @Override
    public RegulatoryIndicator create(RegulatoryIndicator indicator) {
        return repository.save(indicator);
    }

    @Override
    public List<RegulatoryIndicator> findAll() {
        return repository.findAll();
    }

    @Override
    public RegulatoryIndicator findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Регламентированный показатель с данным id не найден"));
    }

    @Override
    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Регламентированный показатель данным с id не найден");
        }
    }

    @Override
    public RegulatoryIndicator update(Long indicatorId, RegulatoryIndicator updatedIndicator) {
        Optional<RegulatoryIndicator> optional = repository.findById(indicatorId);
        if (optional.isPresent()) {
            RegulatoryIndicator indicator = optional.get();
        }
        RegulatoryIndicator existingIndicator = repository.findById(indicatorId)
                .orElseThrow(() -> new NotFoundException("Regulatory Indicator Not Found"));
        merge(existingIndicator, updatedIndicator);
        return repository.save(existingIndicator);
    }

    private void merge(RegulatoryIndicator existingIndicator, RegulatoryIndicator updatedIndicator) {
        if (updatedIndicator.getDate() != null) {
            existingIndicator.setDate(updatedIndicator.getDate());
        }
        if (updatedIndicator.getMrpValue() != null) {
            existingIndicator.setMrpValue(updatedIndicator.getMrpValue());
        }
        if (updatedIndicator.getMzpValue() != null) {
            existingIndicator.setMzpValue(updatedIndicator.getMzpValue());
        }
        if (updatedIndicator.getDescription() != null) {
            existingIndicator.setDescription(updatedIndicator.getDescription());
        }
    }
}