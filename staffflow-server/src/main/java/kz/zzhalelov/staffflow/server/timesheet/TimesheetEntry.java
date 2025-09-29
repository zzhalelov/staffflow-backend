package kz.zzhalelov.staffflow.server.timesheet;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "timesheet_entries")
public class TimesheetEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timesheet_id")
    private Timesheet timesheet;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimesheetDay> days = new ArrayList<>();
}