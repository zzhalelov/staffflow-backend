package kz.zzhalelov.staffflow.gateway.client;

import kz.zzhalelov.staffflow.gateway.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class EmployeeClient {
    private final RestTemplate restTemplate;

    public EmployeeClient(@Qualifier("staffflowServerRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Object> create(EmployeeDto dto) {
        return restTemplate.postForEntity("/api/employees", dto, Object.class);
    }

    public ResponseEntity<Object> findAll() {
        return restTemplate.getForEntity("/api/employees", Object.class);
    }

    public ResponseEntity<Object> delete(long employeeId) {
        return restTemplate.exchange("/api/employees/{employeeId}", HttpMethod.DELETE,
                null,
                Object.class,
                Map.of("employeeId", employeeId));
    }

    public ResponseEntity<Object> findById(long employeeId) {
        return restTemplate.getForEntity("/api/employees/{employeeId}",
                Object.class,
                Map.of("employeeId", employeeId));
    }

    public ResponseEntity<Object> findByLastName(String lastName) {
        return restTemplate.getForEntity("/api/employees/find-by-lastname/{lastName}",
                Object.class,
                Map.of("lastName", lastName));
    }

    public ResponseEntity<Object> update(long employeeId, Object updateDto) {
        return restTemplate.exchange(
                "/api/employees/{employeeId}",
                HttpMethod.PATCH,
                new HttpEntity<>(updateDto),
                Object.class,
                Map.of("employeeId", employeeId)
        );
    }
}