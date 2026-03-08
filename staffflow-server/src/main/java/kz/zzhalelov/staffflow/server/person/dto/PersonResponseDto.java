package kz.zzhalelov.staffflow.server.person.dto;

import kz.zzhalelov.staffflow.server.identityDocument.dto.IdentityDocumentResponseDto;
import kz.zzhalelov.staffflow.server.person.GenderType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonResponseDto {
    Long id;
    String firstName;
    String lastName;
    LocalDate birthdate;
    String iin;
    GenderType gender;
    String citizenship;
    String phone;
    String email;
    String address;
    List<IdentityDocumentResponseDto> documents = new ArrayList<>();

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    boolean deleted;
    LocalDateTime deletedAt;
    String deletedBy;
}