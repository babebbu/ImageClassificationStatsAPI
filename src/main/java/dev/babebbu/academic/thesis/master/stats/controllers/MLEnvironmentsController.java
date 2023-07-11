package dev.babebbu.academic.thesis.master.stats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.babebbu.academic.thesis.master.stats.models.entities.ApplicationType;
import dev.babebbu.academic.thesis.master.stats.models.entities.MLEnvironment;
import dev.babebbu.academic.thesis.master.stats.models.entities.NetworkTier;
import dev.babebbu.academic.thesis.master.stats.models.entities.Runtime;
import dev.babebbu.academic.thesis.master.stats.models.requests.MLEnvironmentRequest;
import dev.babebbu.academic.thesis.master.stats.repositories.ApplicationTypesRepository;
import dev.babebbu.academic.thesis.master.stats.repositories.MLEnvironmentsRepository;
import dev.babebbu.academic.thesis.master.stats.repositories.RuntimesRepository;
import dev.babebbu.academic.thesis.master.stats.repositories.TiersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("ml-environments")
@RequiredArgsConstructor
public class MLEnvironmentsController {

    private final MLEnvironmentsRepository repository;
    private final ApplicationTypesRepository applicationTypesRepository;
    private final TiersRepository networkTiersRepository;
    private final RuntimesRepository runtimesRepository;
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
    public Object create(@RequestBody MLEnvironmentRequest request) {
        Optional<NetworkTier> networkTier = networkTiersRepository.findById(request.getTier());
        Optional<ApplicationType> applicationType = applicationTypesRepository.findById(request.getApplicationType());
        Optional<Runtime> runtime = runtimesRepository.findById(request.getRuntime());

        if (networkTier.isEmpty() || applicationType.isEmpty() || runtime.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Some of arguments are not exist in the database.",
                "exists", Map.of(
                    "tier", networkTier.isPresent(),
                    "applicationType", applicationType.isPresent(),
                    "runtime", runtime.isPresent()
                )
            ));
        }

        MLEnvironment entity = MLEnvironment.builder()
            .id(request.getName().toLowerCase().replace(" ", "-"))
            .name(request.getName())
            .tier(networkTier.get())
            .applicationType(applicationType.get())
            .runtime(runtime.get())
            .build();

        return repository.save(entity);
    }

    @PutMapping("/{slug}")
    public Object update(@PathVariable("slug") String slug, @RequestBody MLEnvironmentRequest request) {
        Optional<MLEnvironment> record = repository.findById(slug);

        if (record.isEmpty()) {
            return notFoundError();
        }

        Optional<NetworkTier> networkTier = networkTiersRepository.findById(request.getTier());
        Optional<ApplicationType> applicationType = applicationTypesRepository.findById(request.getApplicationType());
        Optional<Runtime> runtime = runtimesRepository.findById(request.getRuntime());

        if (networkTier.isEmpty() || applicationType.isEmpty() || runtime.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Some of arguments are not exist in the database.",
                "exists", Map.of(
                    "tier", networkTier.isPresent(),
                    "applicationType", applicationType.isPresent(),
                    "runtime", runtime.isPresent()
                )
            ));
        }

        MLEnvironment entity = MLEnvironment.builder()
            .id(slug)
            .name(request.getName())
            .tier(networkTier.get())
            .applicationType(applicationType.get())
            .runtime(runtime.get())
            .build();

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

    private Object notFoundError() {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "ML Environment not found"));
    }

}
