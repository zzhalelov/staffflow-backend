package kz.zzhalelov.staffflow.server.bonus;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.organization.Organization;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private Month month;

    private int year;

    private LocalDate createdAt;

    @OneToMany(mappedBy = "bonus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonusEntry> entries = new ArrayList<>();
}