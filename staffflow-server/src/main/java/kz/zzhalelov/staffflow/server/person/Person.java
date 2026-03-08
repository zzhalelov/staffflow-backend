package kz.zzhalelov.staffflow.server.person;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.common.BaseEntity;
import kz.zzhalelov.staffflow.server.identityDocument.IdentityDocument;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "persons")
@SQLDelete(sql = """
        update persons
        set deleted = true,
            deleted_at = now(),
            deleted_by = current_user
            where id = ?
        """)
public class Person extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "iin", length = 12, unique = true, nullable = false)
    private String iin;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    GenderType gender;

    @Column(name = "citizenship", length = 20)
    private String citizenship;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "address", length = 100)
    private String address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    List<IdentityDocument> documents = new ArrayList<>();

    public void addDocument(IdentityDocument document) {
        documents.add(document);
        document.setPerson(this);
    }
}