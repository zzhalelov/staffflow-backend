package kz.zzhalelov.staffflow.server.laborContract;

import java.util.List;

public interface LaborContractService {
    LaborContract create(LaborContract laborContract);

    List<LaborContract> findAll();

    LaborContract findById(long id);

    LaborContract update(long id, LaborContract updatedContract);

    LaborContract updateStatus(long id, LaborContractStatus newStatus);

    void delete(long id);
}