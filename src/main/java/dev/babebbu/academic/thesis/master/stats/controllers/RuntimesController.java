package dev.babebbu.academic.thesis.master.stats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.babebbu.academic.thesis.master.stats.models.entities.Runtime;
import dev.babebbu.academic.thesis.master.stats.models.requests.RuntimeRequest;
import dev.babebbu.academic.thesis.master.stats.repositories.RuntimesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("runtimes")
@RequiredArgsConstructor
public class RuntimesController {

    private final RuntimesRepository repository;
    private final ObjectMapper objectMapper;

    @GetMapping
    public Object list() {
        return repository.findAll(Pageable.unpaged());
    }

    @GetMapping("/{slug}")
    public Object get(@PathVariable("slug") final String slug) {
        return repository.findById(slug);
    }

    @PostMapping
    public Object create(@RequestBody RuntimeRequest request) {
        Runtime entity = getEntityFromRequest(request);
        entity.setId(request.getName().toLowerCase().replace(" ", "-"));
        return repository.save(entity);
    }

    @PutMapping("/{slug}")
    public Object update(@PathVariable("slug") String slug, @RequestBody RuntimeRequest request) {
        Optional<Runtime> record = repository.findById(slug);

        if (record.isEmpty()) {
            return notFoundError();
        }

        Runtime entity = getEntityFromRequest(request);
        entity.setId(slug);

        return repository.save(entity);
    }

    @DeleteMapping("{slug}")
    public Object delete(@PathVariable("slug") String slug) {
        if (!repository.existsById(slug)) {
            return notFoundError();
        }
        repository.deleteById(slug);
        return ResponseEntity.ok();
    }

    private Runtime getEntityFromRequest(Object request) {
        return objectMapper.convertValue(request, Runtime.class);
    }

    private Object notFoundError() {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "Runtime not found"));
    }

}
