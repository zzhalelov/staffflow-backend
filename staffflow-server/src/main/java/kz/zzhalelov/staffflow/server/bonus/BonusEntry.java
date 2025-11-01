package kz.zzhalelov.staffflow.server.bonus;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bonus_entries")
public class BonusEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bonus_id")
    private Bonus bonus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "bonusEntry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BonusDetail> details = new ArrayList<>();
}