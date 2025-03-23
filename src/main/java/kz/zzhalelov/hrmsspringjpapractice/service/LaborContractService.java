package kz.zzhalelov.hrmsspringjpapractice.service;

import kz.zzhalelov.hrmsspringjpapractice.model.LaborContract;

import java.util.List;

public interface LaborContractService {
    LaborContract create(LaborContract laborContract);

    List<LaborContract> findAll();
}