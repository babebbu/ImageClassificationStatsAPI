package dev.babebbu.academic.thesis.master.stats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.babebbu.academic.thesis.master.stats.models.entities.Device;
import dev.babebbu.academic.thesis.master.stats.models.entities.Record;
import dev.babebbu.academic.thesis.master.stats.models.requests.RecordRequest;
import dev.babebbu.academic.thesis.master.stats.repositories.DevicesRepository;
import dev.babebbu.academic.thesis.master.stats.repositories.RecordsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("records")
@RequiredArgsConstructor
public class RecordsController {

    private final RecordsRepository repository;
    private final DevicesRepository devicesRepository;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public Object list() {
        return repository.findAll(Pageable.unpaged());
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable("id") final int id) {
        return repository.findById(id);
    }

    @PostMapping
    public Object create(@RequestBody RecordRequest request) {
        Optional<Device> device = devicesRepository.findById(request.getDevice().toLowerCase());

        if (device.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Some of arguments are not exist in the database.",
                "exists", Map.of(
                    "device", false
                )
            ));
        }

        Record entity = Record.builder()
            .device(device.get())
            .inferenceTime(request.getInferenceTime())
            .executionTime(request.getExecutionTime())
            .imageFileName(request.getImageFileName())
            .prediction(request.getPrediction())
            .actual(request.getActual())
            .accurate(request.isAccurate())
            .confidence(request.getConfidence())
            .dataTransfer(request.getDataTransfer())
            .build();

        return repository.save(entity);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable("id") int id, @RequestBody RecordRequest request) {
        Optional<Record> record = repository.findById(id);

        if (record.isEmpty()) {
            return notFoundError();
        }

        Optional<Device> device = devicesRepository.findById(request.getDevice().toLowerCase());

        if (device.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Some of arguments are not exist in the database.",
                "exists", Map.of(
                    "device", false
                )
            ));
        }

        Record entity = Record.builder()
            .id(id)
            .device(device.get())
            .inferenceTime(request.getInferenceTime())
            .executionTime(request.getExecutionTime())
            .imageFileName(request.getImageFileName())
            .prediction(request.getPrediction())
            .actual(request.getActual())
            .accurate(request.isAccurate())
            .confidence(request.getConfidence())
            .dataTransfer(request.getDataTransfer())
            .build();

        return repository.save(entity);
    }

    @DeleteMapping("{id}")
    public Object delete(@PathVariable("id") int id) {
        if (!repository.existsById(id)) {
            return notFoundError();
        }
        repository.deleteById(id);
        return ResponseEntity.ok();
    }

    private Object notFoundError() {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "Record not found"));
    }

}
