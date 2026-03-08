package kz.zzhalelov.staffflow.server.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentityDocumentResponseDto;
import kz.zzhalelov.staffflow.server.person.GenderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Обновление физического лица")
public class PersonUpdateDto {
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @Schema(description = "Имя", example = "John")
    String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(description = "Фамилия", example = "Doe")
    String lastName;

    @NotNull(message = "Birthdate cannot be null")
    @Past(message = "Birthdate must be in the past")
    @Schema(description = "Дата рождения", example = "1990-01-01")
    LocalDate birthdate;

    @NotBlank(message = "IIN cannot be blank")
    @Size(min = 12, max = 12, message = "IIN must be 12 characters long")
    @Schema(description = "ИИН", example = "900101123456")
    String iin;

    @NotNull(message = "Gender cannot be null")
    @Schema(description = "Пол", example = "MALE")
    GenderType gender;

    @NotBlank(message = "Citizenship cannot be blank")
    @Size(min = 2, max = 50, message = "Citizenship must be between 2 and 50 characters")
    @Schema(description = "Страна гражданства", example = "Kazakhstan")
    String citizenship;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters")
    @Schema(description = "Телефон", example = "+77011234567")
    String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Электронная почта", example = "john.doe@example.com")
    String email;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Schema(description = "Адрес", example = "Almaty, Abay Ave 1")
    String address;

    @Schema(description = "Список документов, связанных с физлицом")
    List<IdentityDocumentResponseDto> documents = new ArrayList<>();
}