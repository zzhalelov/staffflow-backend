package kz.zzhalelov.staffflow.server.laborContract;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.department.Department;
import kz.zzhalelov.staffflow.server.employee.Employee;
import kz.zzhalelov.staffflow.server.organization.Organization;
import kz.zzhalelov.staffflow.server.position.Position;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "labor_contracts")
public class LaborContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate hireDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    
    @Enumerated(EnumType.STRING)
    private LaborContractStatus status = LaborContractStatus.NOT_SIGNED;
}