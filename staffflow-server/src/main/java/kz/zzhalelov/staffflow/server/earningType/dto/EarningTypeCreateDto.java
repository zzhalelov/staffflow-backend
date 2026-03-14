package kz.zzhalelov.staffflow.server.earningType.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Данные для создания вида начисления")
public class EarningTypeCreateDto {
    @Schema(description = "Наименование", example = "Оклад по дням")
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Название должно быть от 3 до 100 символов")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё].*", message = "Наименование должно начинаться с буквы")
    String name;

    @Schema(description = "Код", example = "ОКЛДН")
    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 20, message = "Код должен быть от 2 до 20 символов")
    @Pattern(regexp = "^[A-ZА-Я0-9_]*$", message = "Код должен содержать только заглавные буквы, цифры и подчеркивание")
    String code;

    @Schema(description = "Включается ли в ФОТ")
    Boolean includeInFot;

    @Schema(description = "Включается ли в расчет среднего заработка")
    Boolean includeInAverageSalaryCalc;

    @Schema(description = "Индексируемый ли заработок")
    Boolean indexable;

    @Schema(description = "Описание для вида начисления")
    @Size(max = 500, message = "Описание не может превышать 500 символов")
    String description;
}