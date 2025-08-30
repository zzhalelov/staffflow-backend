package kz.zzhalelov.staffflow.server.absence;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "absences")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private AbsenceType type;

    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    @Enumerated(EnumType.STRING)
    private AbsenceStatus status = AbsenceStatus.NOT_APPROVED;
}