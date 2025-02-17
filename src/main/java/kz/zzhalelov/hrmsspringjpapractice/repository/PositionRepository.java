package kz.zzhalelov.hrmsspringjpapractice.repository;

import kz.zzhalelov.hrmsspringjpapractice.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}