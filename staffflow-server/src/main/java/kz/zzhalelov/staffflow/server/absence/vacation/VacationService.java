package kz.zzhalelov.staffflow.server.absence.vacation;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface VacationService {
    Vacation create(Vacation vacation, long employeeId, Month month, LocalDate startDate, LocalDate endDate);

    List<Vacation> findAll();

    Vacation findById(long id);

    void delete(long id);

    Vacation update(long id, Vacation vacation);
}
