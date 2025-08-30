package kz.zzhalelov.staffflow.gateway.client;

import kz.zzhalelov.staffflow.gateway.dto.PositionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class PositionClient {
    private final RestTemplate restTemplate;

    public PositionClient(@Value("${staffflow-server.url}") String url,
                          RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> create(PositionDto dto) {
        return restTemplate.postForEntity("/api/positions", dto, Object.class);
    }

    public ResponseEntity<Object> findAll() {
        return restTemplate.getForEntity("/api/positions", Object.class);
    }

    public ResponseEntity<Object> findById(long positionId) {
        return restTemplate.getForEntity("/api/positions/{positionId}",
                Object.class,
                Map.of("posititionId", positionId));
    }

    public ResponseEntity<Object> delete(long positionId) {
        return restTemplate.exchange("api/positions/{positionId}",
                HttpMethod.DELETE,
                null,
                Object.class,
                Map.of("positionId", positionId));
    }
}