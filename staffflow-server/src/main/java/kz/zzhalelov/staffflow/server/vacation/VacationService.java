package kz.zzhalelov.staffflow.server.vacation;

import java.util.List;

public interface VacationService {
    Vacation create(Long employeeId, Long organizationId, Vacation vacation);

    List<Vacation> findAll();

    Vacation findById(long id);

    void delete(long id);

    Vacation update(long id, Vacation updatedOrganization);
}
