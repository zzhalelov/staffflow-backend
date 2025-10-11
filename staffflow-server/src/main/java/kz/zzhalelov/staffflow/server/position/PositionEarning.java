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
@Table(name = "position_earnings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionEarning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_type_id", nullable = false)
    EarningType earningType;

    Double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    Position position;
}