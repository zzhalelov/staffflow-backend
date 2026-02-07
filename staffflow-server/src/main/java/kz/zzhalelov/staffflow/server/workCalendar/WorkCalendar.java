package kz.zzhalelov.staffflow.server.workCalendar;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;

@Getter
@Setter
@Entity
@Table(name = "work_calendars")
public class WorkCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Month month;
    int daysIn40HrsWeek;
    int hoursIn40HrsWeek;
    int daysIn36HrsWeek;
    int hoursIn36HrsWeek;
}