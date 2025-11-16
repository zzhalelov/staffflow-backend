package kz.zzhalelov.staffflow.server.absence.vacation;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.absence.Absence;
import kz.zzhalelov.staffflow.server.absence.AbsenceStatus;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Getter
@Setter
@Entity
@Table(name = "vacations")
public class Vacation extends Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private AbsenceStatus status;

    @Enumerated(EnumType.STRING)
    private VacationType vacationType;

    private Month month;
    private Integer year;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    @Override
    public BigDecimal calculate() {
        return super.calculate();
    }
}