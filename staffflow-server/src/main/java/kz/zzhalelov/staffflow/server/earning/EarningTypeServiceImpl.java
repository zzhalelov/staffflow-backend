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

        // Находим последнюю запись истории и закрываем её (если есть)
        earningTypeHistoryRepository.findTopByEarningTypeOrderByStartDateDesc(existingType)
                .ifPresent(lastHistory -> {
                    lastHistory.setEndDate(LocalDate.now());
                    earningTypeHistoryRepository.save(lastHistory);
                });

        // Сохраняем запись об изменении в историю
        EarningTypeHistory history = new EarningTypeHistory();
        history.setEarningType(existingType);
        history.setStartDate(LocalDate.now());
        history.setIncludeInFot(existingType.getIncludeInFot());
        history.setIncludeInAverageSalaryCalc(existingType.getIncludeInAverageSalaryCalc());
        history.setIsIndexable(existingType.getIsIndexable());
        history.setComment(existingType.getDescription());

        earningTypeHistoryRepository.save(history);

        existingType.setName(updatedEarningType.getName());
        existingType.setCode(updatedEarningType.getCode());
        existingType.setIncludeInFot(updatedEarningType.getIncludeInFot());
        existingType.setIncludeInAverageSalaryCalc(updatedEarningType.getIncludeInAverageSalaryCalc());
        existingType.setIsIndexable(updatedEarningType.getIsIndexable());
        existingType.setDescription(updatedEarningType.getDescription());

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
        if (earningTypeRepository.findById(id).isPresent()) {
            earningTypeRepository.deleteById(id);
        } else {
            throw new NotFoundException("EarningType not found");
        }
    }

    @Override
    public List<EarningTypeHistory> findHistoryByEarningTypeId(Long earningTypeId) {
        return earningTypeHistoryRepository.findAllByEarningTypeIdOrderByStartDateDesc(earningTypeId);
    }
}