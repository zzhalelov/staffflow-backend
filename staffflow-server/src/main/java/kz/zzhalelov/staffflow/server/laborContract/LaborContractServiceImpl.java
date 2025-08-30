package kz.zzhalelov.staffflow.server.laborContract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaborContractServiceImpl implements LaborContractService {
    private final LaborContractRepository laborContractRepository;

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
        return laborContractRepository.findById(id).orElseThrow();
    }

    @Override
    public LaborContract update(LaborContract laborContract) {
        return laborContractRepository.save(laborContract);
    }
}