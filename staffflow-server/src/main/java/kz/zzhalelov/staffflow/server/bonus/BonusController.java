package kz.zzhalelov.staffflow.server.bonus;

import kz.zzhalelov.staffflow.server.bonus.dto.BonusCreateDto;
import kz.zzhalelov.staffflow.server.bonus.dto.BonusMapper;
import kz.zzhalelov.staffflow.server.bonus.dto.BonusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bonuses")
@RequiredArgsConstructor
public class BonusController {

    private final BonusRepository bonusRepository;
    private final BonusMapper bonusMapper;

    @GetMapping
    public PagedModel<Bonus> getAll(@ParameterObject Pageable pageable) {
        Page<Bonus> bonuses = bonusRepository.findAll(pageable);
        return new PagedModel<>(bonuses);
    }

    @GetMapping("/{id}")
    public Bonus getOne(@PathVariable Long id) {
        Optional<Bonus> bonusOptional = bonusRepository.findById(id);
        return bonusOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Bonus> getMany(@RequestParam List<Long> ids) {
        return bonusRepository.findAllById(ids);
    }

    @PostMapping
    public BonusResponseDto create(@RequestBody BonusCreateDto dto) {
        Bonus bonus = bonusMapper.fromCreate(dto);
        return bonusMapper.toResponse(bonus);
    }

//    @PatchMapping("/{id}")
//    public Bonus patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
//        Bonus bonus = bonusRepository.findById(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
//
//        bonusMapper.readerForUpdating(bonus).readValue(patchNode);
//
//        return bonusRepository.save(bonus);
//    }

//    @PatchMapping
//    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
//        Collection<Bonus> bonuses = bonusRepository.findAllById(ids);
//
//        for (Bonus bonus : bonuses) {
//            bonusMapper.readerForUpdating(bonus).readValue(patchNode);
//        }
//
//        List<Bonus> resultBonuses = bonusRepository.saveAll(bonuses);
//        return resultBonuses.stream()
//                .map(Bonus::getId)
//                .toList();
//    }

    @DeleteMapping("/{id}")
    public Bonus delete(@PathVariable Long id) {
        Bonus bonus = bonusRepository.findById(id).orElse(null);
        if (bonus != null) {
            bonusRepository.delete(bonus);
        }
        return bonus;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        bonusRepository.deleteAllById(ids);
    }
}
