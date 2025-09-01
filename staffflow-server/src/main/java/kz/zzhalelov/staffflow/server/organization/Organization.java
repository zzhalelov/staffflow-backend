package kz.zzhalelov.staffflow.server.organization;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type")
    private OrganizationType organizationType;
    @NotBlank(message = "Полное наименование не заполнено")
    @Column(name = "full_name")
    private String fullName;
    @NotBlank(message = "Короткое наименование не заполнено")
    @Column(name = "short_name")
    private String shortName;
    @NotBlank(message = "ИИН/БИН должен быть заполнен")
    @Column(name = "id_number")
    private String idNumber;
    @Column(name = "has_branches")
    private Boolean hasBranches;
    @Column(name = "is_branch")
    private Boolean isBranch;
    private String address;
}