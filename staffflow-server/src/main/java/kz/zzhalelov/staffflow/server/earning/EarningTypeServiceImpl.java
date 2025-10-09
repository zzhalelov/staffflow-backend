package kz.zzhalelov.staffflow.server.earning;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarningTypeServiceImpl implements EarningTypeService {
    private final EarningTypeRepository earningTypeRepository;
    private final EarningTypeHistoryRepository earningTypeHistoryRepository;

    @Override
    public EarningType create(EarningType earningType) {
        return earningTypeRepository.save(earningType);
    }

    @Override
    public EarningType update(long id, EarningType updatedEarningType) {
        EarningType existingType = earningTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EarningType not found"));

        // перед изменением сохраняем запись в историю
        EarningTypeHistory history = new EarningTypeHistory();
        history.setEarningType(existingType);
        history.setStartDate(LocalDate.now());
        history.setIncludeInFot(existingType.getIncludeInFot());
        history.setIncludeInAverageSalaryCalc(existingType.getIncludeInAverageSalaryCalc());
        history.setIsIndexable(existingType.getIsIndexable());
        history.setComment(existingType.getDescription());
        earningTypeHistoryRepository.save(history);

        // обновляем актуальные данные в основном виде начисления
        merge(existingType, updatedEarningType);

        return earningTypeRepository.save(existingType);
    }

    @Override
    public List<EarningType> findAll() {
        return earningTypeRepository.findAll();
    }

    @Override
    public EarningType findById(long id) {
        return earningTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EarningType not found"));
    }

    @Override
    public void delete(long id) {
        earningTypeRepository.deleteById(id);
    }

    public void merge(EarningType existing, EarningType updated) {
        if (updated.getName() != null && !updated.getName().isBlank()) {
            existing.setName(updated.getName());
        }
        if (updated.getCode() != null && !updated.getCode().isBlank()) {
            existing.setCode(updated.getCode());
        }
        if (updated.getIncludeInFot() != null) {
            existing.setIncludeInFot(updated.getIncludeInFot());
        }
        if (updated.getIncludeInAverageSalaryCalc() != null) {
            existing.setIncludeInAverageSalaryCalc(updated.getIncludeInAverageSalaryCalc());
        }
        if (updated.getIsIndexable() != null) {
            existing.setIsIndexable(updated.getIsIndexable());
        }
        if (updated.getDescription() != null && !updated.getDescription().isBlank()) {
            existing.setDescription(updated.getDescription());
        }
    }
}