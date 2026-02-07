package kz.zzhalelov.staffflow.server.sickLeave;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "sick_leaves")
@Schema(description = "Информация о листе временной нетрудоспособности")
public class SickLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор листа временной нетрудоспособности", example = "12")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @Schema(description = "Идентификатор сотрудника", example = "12")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    @Schema(description = "Идентификатор организации", example = "12")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Статус больничного листа", example = "APPROVED")
    private AbsenceStatus status;

    @Column(nullable = false)
    @Schema(description = "Месяц года", example = "JANUARY")
    private Month month;

    @Column(nullable = false)
    @Schema(description = "Год", example = "2026")
    private Integer year;

    @Schema(description = "Дата начала", example = "2026-01-05")
    private LocalDate startDate;

    @Schema(description = "Дата окончания", example = "2026-01-11")
    private LocalDate endDate;

    @Schema(description = "Описание", example = "Тестовое описание")
    private String description;

    public BigDecimal calculate() {
        return null;
    }
}