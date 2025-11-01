package kz.zzhalelov.staffflow.server.bonus;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.earningType.EarningType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bonus_detail")
public class BonusDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bonus_entry_id")
    private BonusEntry bonusEntry;

    @ManyToOne
    @JoinColumn(name = "earning_type_id")
    private EarningType earningType;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
}