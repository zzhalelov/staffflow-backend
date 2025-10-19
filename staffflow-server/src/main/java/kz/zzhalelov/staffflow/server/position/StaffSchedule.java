package kz.zzhalelov.staffflow.server.position;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.earningType.EarningType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StaffSchedule)) return false;
        StaffSchedule that = (StaffSchedule) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}