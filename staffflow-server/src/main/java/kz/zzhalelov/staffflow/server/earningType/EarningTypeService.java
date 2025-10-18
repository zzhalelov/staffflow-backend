package kz.zzhalelov.staffflow.server.earningType;

import java.util.List;

public interface EarningTypeService {
    EarningType create(EarningType earningType);

    EarningType update(long id, EarningType updatedEarningType);

    List<EarningType> findAll();

    EarningType findById(long id);

    void delete(long id);

    List<EarningTypeHistory> findHistoryByEarningTypeId(Long earningTypeId);
}