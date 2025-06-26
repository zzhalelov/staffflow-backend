package kz.zzhalelov.staffflow.server.service;

import kz.zzhalelov.staffflow.server.model.LaborContract;

import java.util.List;

public interface LaborContractService {
    LaborContract create(LaborContract laborContract);

    List<LaborContract> findAll();

    LaborContract findById(int id);

    LaborContract update(LaborContract laborContract);
}