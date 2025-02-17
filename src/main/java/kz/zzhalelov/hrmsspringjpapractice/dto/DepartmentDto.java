package kz.zzhalelov.hrmsspringjpapractice.dto;

import lombok.Data;

@Data
public class DepartmentDto {
    int id;
    String name;
    int manager;
}