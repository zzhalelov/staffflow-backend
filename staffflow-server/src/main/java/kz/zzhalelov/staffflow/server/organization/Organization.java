package kz.zzhalelov.staffflow.server.organization;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kz.zzhalelov.staffflow.server.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@Table(name = "organizations")
@Schema(description = "Информация об организации")
@SQLDelete(sql = """
        update organizations
        set deleted = true,
            deleted_at = now(),
            deleted_by = current_user
        where id = ?
        """)
public class Organization extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор организации", example = "1")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type")
    @Schema(description = "Тип организации",
            example = "LEGAL_PERSON")
    private OrganizationType organizationType;
    @NotBlank(message = "Полное наименование не заполнено")
    @Column(name = "full_name")
    @Schema(description = "Полное наименование", example = "FullName")
    private String fullName;
    @NotBlank(message = "Короткое наименование не заполнено")
    @Column(name = "short_name")
    @Schema(description = "Короткое наименование", example = "ShortName")
    private String shortName;
    @NotBlank(message = "ИИН/БИН должен быть заполнен")
    @Column(name = "id_number")
    @Schema(description = "БИН организации", example = "012345678912")
    private String idNumber;
    @Column(name = "has_branches")
    @Schema(description = "Имеются ли у организации филиалы?", example = "true", defaultValue = "false")
    private Boolean hasBranches;
    @Column(name = "is_branch")
    @Schema(description = "Это филиал?", example = "true", defaultValue = "false")
    private Boolean isBranch;
    @Schema(description = "Адрес организации", example = "Address")
    private String address;
}