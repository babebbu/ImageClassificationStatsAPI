package dev.babebbu.academic.thesis.master.stats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.babebbu.academic.thesis.master.stats.models.entities.ErrorImage;
import dev.babebbu.academic.thesis.master.stats.models.requests.ErrorImageRequest;
import dev.babebbu.academic.thesis.master.stats.repositories.ErrorImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logs")
@RequiredArgsConstructor
public class LogsController {

    private final ObjectMapper objectMapper;
    private final ErrorImageRepository errorImageRepository;

    @PostMapping("error-images")
    public Object saveErrorImageFileName(@RequestBody ErrorImageRequest request) {
        ErrorImage errorImage = objectMapper.convertValue(request, ErrorImage.class);
        return errorImageRepository.save(errorImage);
    }

}
