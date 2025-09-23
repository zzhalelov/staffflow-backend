package kz.zzhalelov.staffflow.server.laborContract;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.position.Position;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "labor_contract_history")
public class LaborContractHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private LaborContractStatus status;
    @ManyToOne
    @JoinColumn(name = "labor_contract_id", nullable = false)
    private LaborContract laborContract;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    private LocalDateTime changedAt;
}