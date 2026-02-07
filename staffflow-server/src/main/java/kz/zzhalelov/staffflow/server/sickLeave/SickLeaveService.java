package kz.zzhalelov.staffflow.server.sickLeave;

import java.util.List;

public interface SickLeaveService {
    SickLeave create(Long employeeId, Long organizationId, SickLeave sickLeave);

    List<SickLeave> findAll();

    SickLeave findById(long id);

    void delete(long id);

    SickLeave update(long id, SickLeave updatedSickLeave);
}