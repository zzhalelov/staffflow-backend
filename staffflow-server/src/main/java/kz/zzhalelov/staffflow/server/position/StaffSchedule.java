package kz.zzhalelov.staffflow.server.position;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.earning.EarningType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "staff_schedule")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_type_id", nullable = false)
    EarningType earningType;

    private Double amount;
}