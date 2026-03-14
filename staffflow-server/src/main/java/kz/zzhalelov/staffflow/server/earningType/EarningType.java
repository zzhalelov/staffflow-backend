package kz.zzhalelov.staffflow.server.earningType;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@Table(name = "earning_types")
@SQLDelete(sql = """
        update earning_types
        set deleted = true,
            deleted_at = now(),
            deleted_by = current_user
        where id = ?
        """)
public class EarningType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    String name;

    @Column(nullable = false, unique = true, length = 20)
    String code;

    @Column(name = "include_in_fot", nullable = false)
    Boolean includeInFot;

    @Column(name = "include_in_average_salary_calc", nullable = false)
    Boolean includeInAverageSalaryCalc;

    @Column(name = "indexable", nullable = false)
    Boolean indexable;

    @Column(length = 500)
    String description;
}