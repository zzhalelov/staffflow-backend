package kz.zzhalelov.hrmsspringjpapractice.repository;

import kz.zzhalelov.hrmsspringjpapractice.model.LaborContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaborContractRepository extends JpaRepository<LaborContract, Integer> {
}