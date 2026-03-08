package kz.zzhalelov.staffflow.server.hiring;

import java.util.List;

public interface HiringService {
    Hiring create();

    List<Hiring> findAll();

    Hiring findById(long id);

    Hiring update(long id);

    void delete(long id);
}