package kz.zzhalelov.staffflow.server.payroll;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Getter;
import lombok.Setter;

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
}