package kz.zzhalelov.staffflow.server.employee;

import jakarta.persistence.*;
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
    private String iin;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private String email;
    private String phone;
    private String address;
    private String citizenship;
}