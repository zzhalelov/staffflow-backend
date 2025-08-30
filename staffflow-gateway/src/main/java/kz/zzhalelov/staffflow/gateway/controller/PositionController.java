package kz.zzhalelov.staffflow.gateway.controller;

import jakarta.validation.Valid;
import kz.zzhalelov.staffflow.gateway.client.PositionClient;
import kz.zzhalelov.staffflow.gateway.dto.PositionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionClient positionClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody PositionDto dto) {
        return positionClient.create(dto);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return positionClient.findAll();
    }

    @GetMapping("/{positionId}")
    public ResponseEntity<Object> findById(@PathVariable long positionId) {
        return positionClient.findById(positionId);
    }

    @DeleteMapping("/{positionId}")
    public ResponseEntity<Object> deleteById(@PathVariable long positionId) {
        return positionClient.delete(positionId);
    }
}