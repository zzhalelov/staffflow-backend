package kz.zzhalelov.staffflow.gateway.client;

import kz.zzhalelov.staffflow.gateway.dto.DepartmentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class DepartmentClient {
    private final RestTemplate restTemplate;

    public DepartmentClient(@Value("${staffflow-server.url}") String url,
                            RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> create(DepartmentDto dto) {
        return restTemplate.postForEntity("/api/departments", dto, Object.class);
    }

    public ResponseEntity<Object> findAll() {
        return restTemplate.getForEntity("/api/departments", Object.class);
    }

    public ResponseEntity<Object> findById(long departmentId) {
        return restTemplate.getForEntity("/api/departments/{departmentId}",
                Object.class,
                Map.of("departmentId", departmentId));
    }

    public ResponseEntity<Object> delete(long departmentId) {
        return restTemplate.exchange("/api/departments/{departmentId}",
                HttpMethod.DELETE,
                null,
                Object.class,
                Map.of("departmentId", departmentId));
    }
}
