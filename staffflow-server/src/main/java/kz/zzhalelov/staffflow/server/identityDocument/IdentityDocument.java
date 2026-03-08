package kz.zzhalelov.staffflow.server.identityDocument;

import jakarta.persistence.*;
import kz.zzhalelov.staffflow.server.common.BaseEntity;
import kz.zzhalelov.staffflow.server.person.Person;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "identity_documents")
public class IdentityDocument extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private IdentityDocumentType idType;
    private String number;
    private String issuedBy;
    private LocalDate issuedDate;
    private LocalDate validUntil;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}