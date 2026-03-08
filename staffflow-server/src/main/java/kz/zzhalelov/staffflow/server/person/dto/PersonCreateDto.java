package kz.zzhalelov.staffflow.server.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentityDocumentCreateDto;
import kz.zzhalelov.staffflow.server.person.GenderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Данные для создания физического лица")
public class PersonCreateDto {
    @Schema(description = "Имя", example = "Джонни")
    @NotBlank(message = "Firstname is required")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё].*", message = "Имя должно начинаться с буквы")
    String firstName;

    @Schema(description = "Фамилия", example = "Депп")
    @NotBlank(message = "Lastname is required")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё].*", message = "Фамилия должна начинаться с буквы")
    String lastName;

    @Schema(description = "Дата рождения", example = "1990-01-01", pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date is required")
    LocalDate birthdate;

    @Schema(description = "ИИН (12 цифр)", example = "900101123456")
    @NotBlank(message = "Iin is required")
    @Pattern(regexp = "^\\d{12}$", message = "ИИН должен состоять ровно из 12 цифр")
    String iin;

    @Schema(description = "Пол", implementation = GenderType.class)
    @NotNull(message = "Gender is required")
    GenderType gender;

    @Schema(description = "Страна гражданства", example = "Kazakhstan")
    @NotBlank(message = "Citizenship is required")
    String citizenship;

    @Schema(description = "Телефон", example = "+77071234567")
    @NotBlank(message = "Phone is required")
    String phone;

    @Schema(description = "Email", example = "example@mail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Некорректный формат email")
    String email;

    @Schema(description = "Адрес проживания", example = "ул. Абая 10, кв 5")
    @NotBlank(message = "Address is required")
    String address;

    @Schema(description = "Список документов для создания")
    List<IdentityDocumentCreateDto> documents = new ArrayList<>();
}