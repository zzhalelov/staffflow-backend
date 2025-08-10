package kz.zzhalelov.staffflow.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 12, max = 12, message = "Длина ИИН должна быть 12 символов")
    private String iin;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private String email;
    private String phone;
    private String address;
    private String citizenship;
}