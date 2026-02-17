package kz.zzhalelov.staffflow.server.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "departments")
@Schema(description = "Информация о департаменте")
@SQLDelete(sql = """
        update departments
        set deleted = true,
            deleted_at = now(),
            deleted_by = current_user
        where id = ?
        """)
@Where(clause = "deleted = false")
public class Department extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор подразделения", example = "12")
    private Long id;
    @Schema(description = "Наименование подразделения", example = "Administration")
    private String name;
}