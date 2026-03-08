package kz.zzhalelov.staffflow.server.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByIdAndDeletedFalse(Long id);

    Page<Person> findAllByDeletedFalse(Pageable pageable);

    boolean existsByIin(String iin);
}