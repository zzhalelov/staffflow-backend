package kz.zzhalelov.staffflow.server.person;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository repository;

    @Override
    public Person create(Person person) {
        log.info("Creating person: {} {}", person.getFirstName(), person.getLastName());
        if (repository.existsByIin(person.getIin().trim())) {
            log.warn("Creating: Person with IIN {} already exists", person.getIin());
            throw new ConflictException("Person with this IIN already exists: " + person.getIin());
        }
        Person saved = repository.save(person);
        log.info("Person created id={} name={}", saved.getId(), saved.getFirstName());
        return saved;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        log.debug("Fetching persons page={} size={}", pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Person findById(long id) {
        log.debug("Fetching person id={}", id);
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Person not found id={}", id);
                    return new NotFoundException("Person not found");
                });
    }

    @Override
    public Person update(long id, Person updated) {
        log.info("Attempt to update person id={}", updated.getId());
        if (repository.existsByIin(updated.getIin().trim())) {
            log.warn("Updating: Person with IIN {} already exists", updated.getIin());
            throw new ConflictException("Person with this IIN already exists: " + updated.getIin());
        }

        log.info("Attempt to update person id={}", id);
        Person existing = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Updating failed: person not found id={}", id);
                    return new NotFoundException("Person not found");
                });
        merge(existing, updated);
        Person saved = repository.save(existing);
        log.info("Person updated id={} ", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("Attempt to soft delete person id={}", id);
        Person person = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Deleting failed: person not found id={}", id);
                    return new NotFoundException("Person not found");
                });

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        person.markAsDeleted(username);
        log.info("Person id={} soft deleted by {}", id, username);
    }

    @Override
    @Transactional
    public void restore(long id) {
        log.info("Attempt to restore person id={}", id);
        Person person = repository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Restore failed: person not found id={}", id);
                    return new NotFoundException("Person not found");
                });

        if (!person.isDeleted()) {
            log.warn("Restore failed: person id={} is not deleted", id);
            throw new ConflictException("Person not deleted");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "system";

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        person.restore(username);
        log.info("Person id={} restored by {}", id, username);
    }

    private void merge(Person existing, Person updated) {
        if (updated.getIin() != null && !updated.getIin().isBlank()) {
            existing.setIin(updated.getIin());
        }
        if (updated.getFirstName() != null && !updated.getFirstName().isBlank()) {
            existing.setFirstName(updated.getFirstName());
        }
        if (updated.getLastName() != null && !updated.getLastName().isBlank()) {
            existing.setLastName(updated.getLastName());
        }
        if (updated.getBirthdate() != null) {
            existing.setBirthdate(updated.getBirthdate());
        }
        if (updated.getGender() != null) {
            existing.setGender(updated.getGender());
        }
        if (updated.getAddress() != null && !updated.getAddress().isBlank()) {
            existing.setAddress(updated.getAddress());
        }
        if (updated.getCitizenship() != null && !updated.getCitizenship().isBlank()) {
            existing.setCitizenship(updated.getCitizenship());
        }
        if (updated.getEmail() != null && !updated.getEmail().isBlank()) {
            existing.setEmail(updated.getEmail());
        }
        if (updated.getPhone() != null && !updated.getPhone().isBlank()) {
            existing.setPhone(updated.getPhone());
        }
    }
}