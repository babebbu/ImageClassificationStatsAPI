package dev.babebbu.academic.thesis.master.stats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.babebbu.academic.thesis.master.stats.models.entities.*;
import dev.babebbu.academic.thesis.master.stats.models.requests.DeviceRequest;
import dev.babebbu.academic.thesis.master.stats.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("devices")
@RequiredArgsConstructor
public class DevicesController {

    private final DevicesRepository repository;
    private final MLEnvironmentsRepository mlEnvironmentsRepository;
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
    public Object create(@RequestBody DeviceRequest request) {
        Optional<MLEnvironment> mlEnvironment = mlEnvironmentsRepository.findById(request.getEnvironment());

        if (mlEnvironment.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Some of arguments are not exist in the database.",
                "exists", Map.of(
                    "environment", false
                )
            ));
        }

        Device entity = Device.builder()
            .id(request.getName().toLowerCase().replace(" ", "-"))
            .name(request.getName())
            .displayName(request.getDisplayName())
            .description(request.getDescription())
            .hardware(request.getHardware())
            .location(request.getLocation())
            .latency(request.getLatency())
            .mlEnvironment(mlEnvironment.get())
            .build();

        return repository.save(entity);
    }

    @PutMapping("/{slug}")
    public Object update(@PathVariable("slug") String slug, @RequestBody DeviceRequest request) {
        Optional<Device> record = repository.findById(slug);

        if (record.isEmpty()) {
            return notFoundError();
        }

        Optional<MLEnvironment> mlEnvironment = mlEnvironmentsRepository.findById(request.getEnvironment());

        if (mlEnvironment.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Some of arguments are not exist in the database.",
                "exists", Map.of(

                    "environment", false
                )
            ));
        }

        Device entity = Device.builder()
            .id(slug)
            .name(request.getName())
            .displayName(request.getDisplayName())
            .description(request.getDescription())
            .hardware(request.getHardware())
            .location(request.getLocation())
            .latency(request.getLatency())
            .mlEnvironment(mlEnvironment.get())
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
            .body(Map.of("message", "Device not found"));
    }

}
