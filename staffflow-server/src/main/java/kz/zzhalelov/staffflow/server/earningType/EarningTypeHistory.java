package kz.zzhalelov.staffflow.server.earningType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "earning_type_history")
public class EarningTypeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_type_id")
    EarningType earningType;
    LocalDate startDate;
    LocalDate endDate;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean isIndexable;
    String comment;
}