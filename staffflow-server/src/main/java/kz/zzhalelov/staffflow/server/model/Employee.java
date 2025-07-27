package kz.zzhalelov.staffflow.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String iin;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private String email;
    private String phone;
    private String address;
    private String citizenship;
}