package kz.zzhalelov.staffflow.gateway.controller;

import kz.zzhalelov.staffflow.server.dto.employeeDto.EmployeeCreateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class EmployeeClient {
    private final RestTemplate restTemplate;

    public EmployeeClient(@Value("http://localhost:8081") String url,
                          RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> create(EmployeeCreateDto employeeCreateDto) {
        return restTemplate.postForEntity("/api/employees", employeeCreateDto, Object.class);
    }
}