package kz.zzhalelov.staffflow.server.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kz.zzhalelov.staffflow.server.employee.GenderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Создание сотрудника")
public class EmployeeCreateDto {
    @NotBlank(message = "First name is required")
    String firstName;
    @NotBlank(message = "Last name is required")
    String lastName;
    @NotBlank(message = "IIN is required")
    @Size(min = 12, max = 12)
    String iin;
    @NotBlank(message = "Gender is required")
    GenderType gender;
    @Email(message = "Email is incorrect")
    String email;
    @NotBlank(message = "Phone is required")
    @Size(min = 1, max = 12)
    String phone;
    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 50)
    String address;
    @NotBlank(message = "Citizenship is required")
    @Size(min = 1, max = 20)
    String citizenship;
}