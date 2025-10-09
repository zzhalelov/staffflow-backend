package kz.zzhalelov.staffflow.server.position;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.earning.EarningType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PositionEarning> earnings = new ArrayList<>();

    public void addEarning(EarningType type, Double amount) {
        PositionEarning earning = new PositionEarning();
        earning.setEarningType(type);
        earning.setAmount(amount);
        earning.setPosition(this);
        earnings.add(earning);
    }
}