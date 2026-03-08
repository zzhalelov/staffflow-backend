package kz.zzhalelov.staffflow.server.hiring.dto;

import kz.zzhalelov.staffflow.server.hiring.HiringStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HiringResponseDto {
    Long id;
    Long organizationId;
    Long employeeId;
    LocalDate hireDate;
    Long departmentId;
    Long positionId;
    HiringStatus hiringStatus;
    Long probationPeriod;
    String comments;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    boolean deleted;
    LocalDateTime deletedAt;
    String deletedBy;
}