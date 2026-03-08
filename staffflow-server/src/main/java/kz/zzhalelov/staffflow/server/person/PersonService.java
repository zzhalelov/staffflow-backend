package kz.zzhalelov.staffflow.server.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    Person create(Person person);

    Page<Person> findAll(Pageable pageable);

    Person findById(long id);

    Person update(long id, Person updated);

    void delete(long id);

    void restore(long id);
}