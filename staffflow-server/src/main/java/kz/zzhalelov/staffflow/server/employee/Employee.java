package kz.zzhalelov.staffflow.server.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
@Schema(description = "Информация о сотруднике")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор сотрудника", example = "12")
    private Long id;

    @Schema(description = "Имя сотрудника", example = "Сара")
    private String firstName;

    @Schema(description = "Фамилия", example = "Коннор")
    private String lastName;

    @NotBlank(message = "ИИН должен быть заполнен")
    @Column(unique = true, nullable = false)
    @Schema(description = "ИИН сотрудника из 12 символов", example = "123456789012")
    private String iin;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Пол сотрудника", allowableValues = "MALE, FEMALE")
    private GenderType gender;

    @Email(message = "Email is incorrect")
    @Schema(description = "Эл.почта", example = "sarah_connor@gmail.com")
    private String email;

    @Schema(description = "Номер телефона", example = "+77771234567")
    private String phone;

    @Schema(description = "Адрес", example = "Beverly Hills, CA")
    private String address;

    @Schema(description = "Страна гражданства", example = "USA")
    private String citizenship;
}