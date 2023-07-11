package dev.babebbu.academic.thesis.master.stats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.babebbu.academic.thesis.master.stats.models.entities.ApplicationType;
import dev.babebbu.academic.thesis.master.stats.models.requests.ApplicationTypeRequest;
import dev.babebbu.academic.thesis.master.stats.repositories.ApplicationTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("application-types")
@RequiredArgsConstructor
public class ApplicationTypesController {

    private final ApplicationTypesRepository repository;
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
    public Object create(@RequestBody ApplicationTypeRequest request) {
        ApplicationType entity = getEntityFromRequest(request);
        entity.setId(request.getName().toLowerCase().replace(" ", "-"));
        return repository.save(entity);
    }

    @PutMapping("/{slug}")
    public Object update(@PathVariable("slug") String slug, @RequestBody ApplicationTypeRequest request) {
        Optional<ApplicationType> record = repository.findById(slug);

        if (record.isEmpty()) {
            return notFoundError();
        }

        ApplicationType entity = getEntityFromRequest(request);
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

    private ApplicationType getEntityFromRequest(ApplicationTypeRequest request) {
        return objectMapper.convertValue(request, ApplicationType.class);
    }

    private Object notFoundError() {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "Application Type not found"));
    }

}
