package kz.zzhalelov.staffflow.server.payroll;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.earningType.EarningType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "payroll_details")
public class PayrollDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payroll_entry_id")
    private PayrollEntry payrollEntry;

    @ManyToOne
    @JoinColumn(name = "earning_type_id")
    private EarningType earningType;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    private Long workedDays;
    private Long plannedDays;
    private BigDecimal grossSum;
}