package kz.zzhalelov.staffflow.server.timesheet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "timesheet_days")
public class TimesheetDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "entry_id", nullable = false)
    private TimesheetEntry entry;
    private int dayOfMonth; //1-31
    @Enumerated(EnumType.STRING)
    private DayStatus status;
}