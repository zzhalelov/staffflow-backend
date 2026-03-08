package kz.zzhalelov.staffflow.server.person;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentMatchers.*;
import org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void create_shouldCreate() {
        Person person = new Person();
        person.setId(1L);
        person.setIin("123456789012");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(LocalDate.of(1990, 1, 1));
        person.setGender(GenderType.MALE);
        person.setAddress("LA, USA");
        person.setCitizenship("USA");
        person.setEmail("test@gmail.com");
        person.setPhone("+7 777 123 45 67");
        person.setDocuments(List.of());

        Mockito
                .when(personRepository.existsByIin(Mockito.anyString()))
                .thenReturn(false);

        Mockito
                .when(personRepository.save(any(Person.class)))
                .thenReturn(person);

        Person saved = personService.create(person);

        assertEquals(person.getId(), saved.getId());
        assertEquals(person.getIin(), saved.getIin());
        assertEquals(person.getAddress(), saved.getAddress());
        assertEquals(person.getBirthdate(), saved.getBirthdate());
        assertEquals(person.getCitizenship(), saved.getCitizenship());
        assertEquals(person.getEmail(), saved.getEmail());
        assertEquals(person.getFirstName(), saved.getFirstName());
        assertEquals(person.getLastName(), saved.getLastName());
        assertEquals(person.getGender(), saved.getGender());
        assertEquals(person.getPhone(), saved.getPhone());
        assertEquals(person.getDocuments(), saved.getDocuments());
        Mockito.verify(personRepository).save(any(Person.class));
    }

    @Test
    void create_shouldThrow_whenIinAlreadyExists() {
        Person existing = new Person();
        existing.setIin("123456789012");

        Person newPerson = new Person();
        newPerson.setIin("123456789012");

        Mockito
                .when(personRepository.existsByIin("123456789012"))
                .thenReturn(true);

        assertThrows(ConflictException.class, () ->
                personService.create(newPerson));
    }

    @Test
    void findAll_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Person existing1 = new Person();
        existing1.setId(1L);
        existing1.setIin("123456789012");
        existing1.setFirstName("John");
        existing1.setLastName("Doe");
        existing1.setBirthdate(LocalDate.of(1990, 1, 1));
        existing1.setGender(GenderType.MALE);
        existing1.setAddress("LA, USA");
        existing1.setCitizenship("USA");
        existing1.setEmail("test@gmail.com");
        existing1.setPhone("+7 777 123 45 67");
        existing1.setDocuments(List.of());

        Person existing2 = new Person();
        existing2.setId(1L);
        existing2.setIin("123456789012");
        existing2.setFirstName("John");
        existing2.setLastName("Doe");
        existing2.setBirthdate(LocalDate.of(1990, 1, 1));
        existing2.setGender(GenderType.MALE);
        existing2.setAddress("LA, USA");
        existing2.setCitizenship("USA");
        existing2.setEmail("test@gmail.com");
        existing2.setPhone("+7 777 123 45 67");
        existing2.setDocuments(List.of());

        Page<Person> page = new PageImpl<>(List.of(existing1, existing2), pageable, 2);

        Mockito
                .when(personRepository.findAllByDeletedFalse(pageable))
                .thenReturn(page);

        Page<Person> savedPersons = personService.findAll(pageable);
        assertEquals(2, savedPersons.getContent().size());
        assertEquals(1, savedPersons.getTotalPages());
        assertEquals(2, savedPersons.getTotalElements());
        assertEquals(existing1.getId(), savedPersons.getContent().get(0).getId());
        assertEquals(existing2.getId(), savedPersons.getContent().get(1).getId());
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void restore() {
    }
}