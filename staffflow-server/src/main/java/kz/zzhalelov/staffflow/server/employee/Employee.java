package kz.zzhalelov.staffflow.server.employee;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "ИИН должен быть заполнен")
    @Column(unique = true, nullable = false)
    private String iin;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @Email(message = "Email is incorrect")
    private String email;
    private String phone;
    private String address;
    private String citizenship;
}