package kz.zzhalelov.staffflow.server.hiring;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hirings")
@RequiredArgsConstructor
public class HiringController {
    private final HiringService hiringService;

    @GetMapping
    public List<Hiring> findAll() {
        return null;
    }
}