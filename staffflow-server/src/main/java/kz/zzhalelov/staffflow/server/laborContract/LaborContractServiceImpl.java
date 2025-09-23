package kz.zzhalelov.staffflow.server.laborContract;

import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaborContractServiceImpl implements LaborContractService {
    private final LaborContractRepository laborContractRepository;
    private final LaborContractHistoryRepository laborContractHistoryRepository;

    @Override
    public LaborContract create(LaborContract laborContract) {
        return laborContractRepository.save(laborContract);
    }

    @Override
    public List<LaborContract> findAll() {
        return laborContractRepository.findAll();
    }

    @Override
    public LaborContract findById(long id) {
        return laborContractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));
    }

    @Override
    public void delete(long id) {
        LaborContract laborContract = findById(id);
        if (laborContract.getStatus().equals(LaborContractStatus.HIRED)) {
            throw new IllegalStateException("Contract with id " + id + " is already hired!");
        }
        if (laborContract.getStatus().equals(LaborContractStatus.RESIGNED)) {
            throw new IllegalStateException("Contract with id " + id + " is resigned!");
        }
        laborContractRepository.deleteById(id);
    }

    @Override
    @Transactional
    public LaborContract updateStatus(long id, LaborContractStatus newStatus) {
        LaborContract contract = laborContractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));

        // обновляем статус в основном договоре
        contract.setStatus(newStatus);
        laborContractRepository.save(contract);

        // добавляем запись в историю
        LaborContractHistory history = new LaborContractHistory();
        history.setLaborContract(contract);
        history.setStatus(newStatus);
        history.setChangedAt(LocalDateTime.now());
        laborContractHistoryRepository.save(history);
        return contract;
    }

    @Override
    public LaborContract update(long id, LaborContract updatedLaborContract) {
        LaborContract existingLaborContract = laborContractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));

        // перед изменением сохраняем запись в историю
        LaborContractHistory history = new LaborContractHistory();
        history.setLaborContract(existingLaborContract);
        history.setStatus(existingLaborContract.getStatus());
        history.setDepartment(existingLaborContract.getDepartment());
        history.setPosition(existingLaborContract.getPosition());
        history.setChangedAt(LocalDateTime.now());
        laborContractHistoryRepository.save(history);

        // обновляем актуальные данные в основном договоре
        merge(existingLaborContract, updatedLaborContract);

        return laborContractRepository.save(existingLaborContract);
    }

    public void merge(LaborContract existingLaborContract, LaborContract updated) {
        if (updated.getOrganization() != null && updated.getOrganization().getId() != null) {
            existingLaborContract.setOrganization(updated.getOrganization());
        }
        if (updated.getEmployee() != null && updated.getEmployee().getId() != null) {
            existingLaborContract.setEmployee(updated.getEmployee());
        }
        if (updated.getDepartment() != null && updated.getDepartment().getId() != null) {
            existingLaborContract.setDepartment(updated.getDepartment());
        }
        if (updated.getPosition() != null && updated.getPosition().getId() != null) {
            existingLaborContract.setPosition(updated.getPosition());
        }
        if (updated.getHireDate() != null) {
            existingLaborContract.setHireDate(updated.getHireDate());
        }
        if (updated.getStatus() != null) {
            existingLaborContract.setStatus(updated.getStatus());
        }
    }
}