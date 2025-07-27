package kz.zzhalelov.staffflow.server.repository;

import kz.zzhalelov.staffflow.server.model.LaborContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaborContractRepository extends JpaRepository<LaborContract, Long> {
}