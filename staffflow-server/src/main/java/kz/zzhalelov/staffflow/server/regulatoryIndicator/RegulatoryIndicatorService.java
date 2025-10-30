package kz.zzhalelov.staffflow.server.regulatoryIndicator;

import java.util.List;

public interface RegulatoryIndicatorService {
    RegulatoryIndicator create(RegulatoryIndicator indicator);

    List<RegulatoryIndicator> findAll();

    RegulatoryIndicator findById(Long id);

    RegulatoryIndicator update(Long indicatorId, RegulatoryIndicator indicator);

    void deleteById(Long id);
}