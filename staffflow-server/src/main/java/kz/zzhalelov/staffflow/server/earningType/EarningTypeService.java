package kz.zzhalelov.staffflow.server.earningType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EarningTypeService {
    EarningType create(EarningType earningType);

    EarningType update(long id, EarningType updatedEarningType);

    Page<EarningType> findAll(Pageable pageable);

    EarningType findById(long id);

    void delete(long id);

    List<EarningTypeHistory> findHistoryByEarningTypeId(Long earningTypeId);

    void restore(long id);
}