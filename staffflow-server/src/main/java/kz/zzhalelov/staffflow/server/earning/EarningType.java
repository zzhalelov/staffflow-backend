package kz.zzhalelov.staffflow.server.earning;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "earning_types")
public class EarningType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    String name;
    String code;
    Boolean includeInFot;
    Boolean includeInAverageSalaryCalc;
    Boolean isIndexable;
    String description;
}