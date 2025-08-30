package kz.zzhalelov.staffflow.server.absence;

import jakarta.validation.constraints.NotNull;
import kz.zzhalelov.staffflow.server.employee.Employee;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO for {@link Absence}
 */
@Value
public class AbsenceCreateDto {
    Employee employee;
    AbsenceType type;
    @NotNull(message = "Должна быть заполнена Дату начала")
    LocalDate startDate;
    @NotNull(message = "Должна быть заполнена Дата окончания")
    LocalDate endDate;
    @NotNull(message = "Не заполнено описание")
    String description;
    @NotNull(message = "Не заполнен статус")
    AbsenceStatus status;
}