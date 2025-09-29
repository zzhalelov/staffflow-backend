package kz.zzhalelov.staffflow.server.timesheet;

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
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private Month month;

    private int year;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "timesheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimesheetEntry> entries = new ArrayList<>();
}