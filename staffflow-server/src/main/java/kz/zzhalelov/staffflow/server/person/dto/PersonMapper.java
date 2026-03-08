package kz.zzhalelov.staffflow.server.person.dto;

import kz.zzhalelov.staffflow.server.person.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public Person fromCreate(PersonCreateDto dto) {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setBirthdate(dto.getBirthdate());
        person.setIin(dto.getIin());
        person.setGender(dto.getGender());
        person.setCitizenship(dto.getCitizenship());
        person.setPhone(dto.getPhone());
        person.setEmail(dto.getEmail());
        person.setAddress(dto.getAddress());
        return person;
    }

    public Person fromUpdate(PersonUpdateDto dto) {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setBirthdate(dto.getBirthdate());
        person.setIin(dto.getIin());
        person.setGender(dto.getGender());
        person.setCitizenship(dto.getCitizenship());
        person.setPhone(dto.getPhone());
        person.setEmail(dto.getEmail());
        person.setAddress(dto.getAddress());
        return person;
    }

    public PersonResponseDto toResponse(Person person) {
        PersonResponseDto dto = new PersonResponseDto();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthdate(person.getBirthdate());
        dto.setIin(person.getIin());
        dto.setGender(person.getGender());
        dto.setCitizenship(person.getCitizenship());
        dto.setPhone(person.getPhone());
        dto.setEmail(person.getEmail());
        dto.setAddress(person.getAddress());

        dto.setCreatedAt(person.getCreatedAt());
        dto.setUpdatedAt(person.getUpdatedAt());
        dto.setCreatedBy(person.getCreatedBy());
        dto.setUpdatedBy(person.getUpdatedBy());
        dto.setDeleted(person.isDeleted());
        dto.setDeletedAt(person.getDeletedAt());
        dto.setDeletedBy(person.getDeletedBy());
        return dto;
    }
}