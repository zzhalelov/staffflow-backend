package kz.zzhalelov.staffflow.server.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.staffflow.server.config.NoSecurityTestConfig;
import kz.zzhalelov.staffflow.server.person.dto.PersonCreateDto;
import kz.zzhalelov.staffflow.server.person.dto.PersonMapper;
import kz.zzhalelov.staffflow.server.person.dto.PersonUpdateDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest({PersonController.class, PersonMapper.class})
@Import(NoSecurityTestConfig.class)
class PersonControllerTest {
    @MockitoBean
    PersonService personService;
    @MockitoBean
    PersonRepository personRepository;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create_shouldReturnCreated() {
        long personId = 1;

        PersonCreateDto createDto = new PersonCreateDto();
        createDto.setIin("123456789012");
        createDto.setFirstName("John ");
        createDto.setLastName("Doe");
        createDto.setGender(GenderType.MALE);
        createDto.setBirthdate(LocalDate.of(1990, 1, 1));
        createDto.setCitizenship("USA");
        createDto.setAddress("LA, USA");
        createDto.setEmail("test@gmail.com");
        createDto.setPhone("+77771234567");

        Mockito
                .when(personService.create(Mockito.any(Person.class)))
                .thenAnswer(i -> {
                    Person argument = i.getArgument(0, Person.class);
                    argument.setId(personId);
                    return argument;
                });

        String json = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(personId))
                .andExpect(jsonPath("$.iin").value(createDto.getIin()))
                .andExpect(jsonPath("$.firstName").value(createDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(createDto.getLastName()));
    }

    @Test
    @SneakyThrows
    void findAll() {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setIin("123456789012");

        Person person2 = new Person();
        person2.setId(2L);
        person2.setIin("321456789021");

        Page<Person> page =
                new PageImpl<>(
                        List.of(person1, person2),
                        PageRequest.of(0, 20),
                        2
                );

        Mockito
                .when(personService.findAll(Mockito.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(person1.getId()))
                .andExpect(jsonPath("$.content[1].id").value(person2.getId()))
                .andExpect(jsonPath("$.content[0].iin").value(person1.getIin()))
                .andExpect(jsonPath("$.content[1].iin").value(person2.getIin()));
    }

    @Test
    @SneakyThrows
    void findById() {
        long id = 1;

        Person person = new Person();
        person.setId(1L);
        person.setIin("123456789012");

        Mockito
                .when(personService.findById(Mockito.anyLong()))
                .thenReturn(person);

        mockMvc.perform(get("/api/persons/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.iin").value(person.getIin()));
    }

    @Test
    @SneakyThrows
    void update_shouldReturnOk() {
        long id = 1L;

        PersonUpdateDto updateDto = new PersonUpdateDto();
        updateDto.setIin("123456789012");
        updateDto.setFirstName("John ");
        updateDto.setLastName("Doe");
        updateDto.setGender(GenderType.MALE);
        updateDto.setBirthdate(LocalDate.of(1990, 1, 1));
        updateDto.setCitizenship("USA");
        updateDto.setAddress("LA, USA");
        updateDto.setEmail("test@gmail.com");
        updateDto.setPhone("+77771234567");

        Person person = new Person();
        person.setId(id);
        person.setIin("111222333444");

        Mockito
                .when(personRepository.findById(Mockito.eq(id)))
                .thenReturn(Optional.of(new Person()));

        Mockito
                .when(personService.update(Mockito.anyLong(), Mockito.any(Person.class)))
                .thenReturn(person);

        String json = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(patch("/api/persons/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.iin").value(person.getIin()));
    }

    @Test
    @SneakyThrows
    void delete_shouldReturnNoContent() {
        long id = 1L;

        mockMvc.perform(delete("/api/persons/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void restore_shouldReturnNoContent() {
        long id = 1L;

        Mockito
                .doNothing()
                .when(personService)
                .restore(id);

        mockMvc.perform(post("/api/persons/{id}/restore", id))
                .andExpect(status().isNoContent());

        Mockito
                .verify(personService, Mockito.times(1))
                .restore(id);
    }
}