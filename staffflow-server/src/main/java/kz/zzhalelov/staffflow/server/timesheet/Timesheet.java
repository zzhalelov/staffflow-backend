package kz.zzhalelov.staffflow.server.timesheet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.organization.Organization;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "timesheets")
@Schema(description = "Информация о табеле учета рабочего времени")
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Индентификатор табеля")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @Schema(description = "Id организации", example = "1")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Месяц года", allowableValues = "JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, " +
            "AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER")
    private Month month;

    @Schema(description = "Год", example = "2025")
    private int year;

    @Schema(description = "Дата создания табеля", example = "2025-10-19T07:01:03.799Z")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список записей в табличной части табеля")
    private List<TimesheetEntry> entries = new ArrayList<>();
}