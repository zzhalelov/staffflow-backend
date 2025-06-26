package kz.zzhalelov.staffflow.server.repository;

import kz.zzhalelov.staffflow.server.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}