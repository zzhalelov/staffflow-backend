package kz.zzhalelov.staffflow.server.payroll;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "payroll_entries")
public class PayrollEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "payrollEntry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PayrollDetail> details = new ArrayList<>();
}