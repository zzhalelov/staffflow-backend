package kz.zzhalelov.staffflow.server.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.zzhalelov.staffflow.server.employee.GenderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Информация о сотруднике")
public class EmployeeResponseDto {
    @Schema(description = "Идентификатор сотрудника", example = "42")
    Long id;

    @Schema(description = "Имя", example = "Alan")
    String firstName;

    @Schema(description = "Фамилия", example = "Wake")
    String lastName;

    @Schema(description = "ИИН", example = "012345678910")
    String iin;

    @Schema(description = "Пол", example = "")
    GenderType gender;

    @Schema(description = "Электронная почта", example = "")
    String email;

    @Schema(description = "Телефон", example = "")
    String phone;

    @Schema(description = "Адрес", example = "")
    String address;

    @Schema(description = "Гражданство", example = "")
    String citizenship;
}