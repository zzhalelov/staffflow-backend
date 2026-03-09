package kz.zzhalelov.staffflow.server.person;

import kz.zzhalelov.staffflow.server.exception.ConflictException;
import kz.zzhalelov.staffflow.server.exception.NotFoundException;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        when(personRepository.existsByIin(Mockito.anyString()))
                .thenReturn(false);

        when(personRepository.save(any(Person.class)))
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

        when(personRepository.existsByIin("123456789012"))
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

        when(personRepository.findAllByDeletedFalse(pageable))
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
        Person existing = new Person();
        existing.setId(1L);
        existing.setIin("123456789012");
        existing.setFirstName("John");
        existing.setLastName("Doe");
        existing.setBirthdate(LocalDate.of(1990, 1, 1));
        existing.setGender(GenderType.MALE);
        existing.setAddress("LA, USA");
        existing.setCitizenship("USA");
        existing.setEmail("test@gmail.com");
        existing.setPhone("+7 777 123 45 67");
        existing.setDocuments(List.of());

        when(personRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.of(existing));

        Person saved = personService.findById(1L);
        assertEquals(existing.getId(), saved.getId());
        assertEquals(existing.getIin(), saved.getIin());
        assertEquals(existing.getFirstName(), saved.getFirstName());
        assertEquals(existing.getLastName(), saved.getLastName());
        assertEquals(existing.getBirthdate(), saved.getBirthdate());
        assertEquals(existing.getGender(), saved.getGender());
        assertEquals(existing.getAddress(), saved.getAddress());
        assertEquals(existing.getCitizenship(), saved.getCitizenship());
        assertEquals(existing.getEmail(), saved.getEmail());
        assertEquals(existing.getPhone(), saved.getPhone());
        assertEquals(existing.getDocuments(), saved.getDocuments());
    }

    @Test
    void findById_shouldThrow_whenPersonNotFound() {
        when(personRepository.findByIdAndDeletedFalse(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> personService.findById(1L)
        );
        Mockito
                .verify(personRepository)
                .findByIdAndDeletedFalse(1L);
    }

    @Test
    void update_shouldSavePerson() {
        Person existing = new Person();
        existing.setId(1L);
        existing.setIin("111111111111");
        existing.setFirstName("OldName");

        Person updated = new Person();
        updated.setIin("123456789012");
        updated.setFirstName("John");
        updated.setLastName("Doe");

        when(personRepository.existsByIin("123456789012"))
                .thenReturn(false);

        when(personRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(personRepository.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Person result = personService.update(1L, updated);

        assertEquals("John", result.getFirstName());
        assertEquals("123456789012", result.getIin());
        Mockito.verify(personRepository).save(existing);
    }

    @Test
    void update_shouldThrow_whenPersonNotFound() {
        long id = 1L;
        Person updated = new Person();
        updated.setIin("123456789012");

        when(personRepository.existsByIin("123456789012"))
                .thenReturn(false);

        when(personRepository.findById(id))
                .thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> personService.update(id, updated));
        assertEquals("Person not found", ex.getMessage());

        Mockito
                .verify(personRepository)
                .existsByIin("123456789012");

        Mockito
                .verify(personRepository)
                .findById(id);

        Mockito
                .verify(personRepository, Mockito.never()).save(any());
    }

    @Test
    void update_shouldThrowConflictException_whenIinAlreadyExists() {
        long id = 1L;
        Person updated = new Person();
        updated.setIin("123456789012");
        updated.setFirstName("NewName");

        when(personRepository.existsByIin("123456789012"))
                .thenReturn(true);

        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> personService.update(id, updated)
        );

        assertEquals("Person with this IIN already exists: 123456789012", ex.getMessage());

        Mockito
                .verify(personRepository)
                .existsByIin("123456789012");

        Mockito
                .verify(personRepository, Mockito.never())
                .findById(anyLong());

        Mockito
                .verify(personRepository, Mockito.never())
                .save(any(Person.class));
    }

    @Test
    void delete_shouldThrowNotFound_withCorrectMessage_whenPersonNotFound() {
        long id = 999L;

        when(personRepository.findById(id))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> personService.delete(id),
                "Метод должен выбросить NotFoundException, если ID не найден"
        );

        assertEquals("Person not found", exception.getMessage());

        Mockito.verify(personRepository).findById(id);

        Mockito.verifyNoMoreInteractions(personRepository);
    }

    @Test
    void delete_shouldMarkAsDeleted_withUsername_whenAuthenticated() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);
        String mockUsername = "active_admin_ivanov"; // Используем уникальное имя

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(mockUsername);

        try {
            personService.delete(id);
            assertTrue(person.isDeleted(), "Флаг deleted должен быть true");
            assertEquals(mockUsername, person.getDeletedBy(), "Имя удалившего должно быть взято из SecurityContext");
            assertNotNull(person.getDeletedAt(), "Дата удаления должна быть проставлена");
            Mockito.verify(personRepository).findById(id);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void delete_shouldMarkAsDeleted_withSystem_whenUserIsAnonymous() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken anonymousToken = mock(AnonymousAuthenticationToken.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(anonymousToken);

        try {
            personService.delete(id);
            assertTrue(person.isDeleted());
            assertEquals("system", person.getDeletedBy(), "Для анонимного входа должен использоваться 'system'");
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void delete_shouldUseSystem_whenAuthIsNull() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        try {
            personService.delete(id);
            assertTrue(person.isDeleted());
            assertEquals("system", person.getDeletedBy());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void delete_shouldUseSystem_whenAnonymousToken() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken auth = mock(AnonymousAuthenticationToken.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);

        try {
            personService.delete(id);
            assertEquals("system", person.getDeletedBy());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldThrowNotFound_whenPersonDoesNotExist() {
        long id = 1L;
        when(personRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(
                NotFoundException.class,
                () -> personService.restore(id)
        );

        assertEquals("Person not found", ex.getMessage());

        Mockito.verify(personRepository).findById(id);
        Mockito.verifyNoMoreInteractions(personRepository);
    }

    @Test
    void restore_shouldThrowConflict_whenPersonIsNotInDeletedState() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        ConflictException ex = assertThrows(
                ConflictException.class,
                () -> personService.restore(id)
        );

        assertEquals("Person not deleted", ex.getMessage());

        Mockito.verify(personRepository).findById(id);

        assertNull(person.getDeletedAt());
    }

    @Test
    void restore_shouldRestoreWithActualUsername_whenAuthenticated() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.markAsDeleted("system");

        String mockUsername = "system";

        when(personRepository.findById(id))
                .thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn(mockUsername);

        try {
            personService.restore(id);
            assertFalse(person.isDeleted(), "Флаг deleted должен стать false после restore");
            Mockito.verify(personRepository).findById(id);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldRestoreWithSystemUsername_whenNotAuthenticated() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.markAsDeleted("system");

        when(personRepository.findById(id))
                .thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        try {
            personService.restore(id);
            assertFalse(person.isDeleted());
            Mockito.verify(personRepository).findById(id);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldThrowNotFound_whenIdNotExists() {
        long id = 999L;
        when(personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> personService.restore(id));
    }

    @Test
    void restore_shouldThrowConflict_whenPersonIsNotDeleted() {
        long id = 1L;
        Person activePerson = new Person();
        activePerson.setId(id);
        activePerson.restore("system");

        when(personRepository.findById(id)).thenReturn(Optional.of(activePerson));

        assertThrows(ConflictException.class, () -> personService.restore(id));
    }

    @Test
    void restore_shouldUseSystem_whenAnonymousToken() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.markAsDeleted("old-user");

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken anonymousToken = mock(AnonymousAuthenticationToken.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(anonymousToken);

        try {
            personService.restore(id);
            assertFalse(person.isDeleted());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    void restore_shouldUseSystem_whenAuthenticationIsNotAuthenticated() {
        long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.markAsDeleted("old-user");

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(false);

        try {
            personService.restore(id);
            assertFalse(person.isDeleted());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}