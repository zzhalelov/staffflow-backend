package kz.zzhalelov.staffflow.server.hiring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.common.BaseEntity;
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
@Table(name = "hirings")
@Schema(description = "Информация о приемах на работу")
public class Hiring extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор приема на работу", example = "12")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @Schema(description = "Идентификатор организации", example = "2")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @Schema(description = "Идентификатор сотрудника", example = "1")
    private Employee employee;

    @Schema(description = "Дата приема на работу", example = "2026-01-05")
    private LocalDate hireDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @Schema(description = "Идентификатор подразделения", example = "1")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @Schema(description = "Идентификатор должности", example = "1")
    private Position position;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Статус приема на работу", example = "APPROVED",
            allowableValues = "NOT_APPROVED, APPROVED, CANCELED")
    private HiringStatus status = HiringStatus.NOT_APPROVED;

    @Schema(description = "Испытательный срок, в месяцах", example = "3")
    private Long probationPeriod;

    @Schema(description = "Комментарии", example = "прием на работу сотрудника")
    private String comments;
}