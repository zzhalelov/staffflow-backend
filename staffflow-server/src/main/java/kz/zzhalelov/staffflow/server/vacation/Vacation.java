package kz.zzhalelov.staffflow.server.vacation;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.common.AbsenceStatus;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.organization.Organization;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Getter
@Setter
@Entity
@Table(name = "vacations")
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AbsenceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "vacation_type", nullable = false)
    private VacationType vacationType;

    @Column(nullable = false)
    private Month month;
    @Column(nullable = false)
    private Integer year;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;


    public BigDecimal calculate() {
        return null;
    }
}