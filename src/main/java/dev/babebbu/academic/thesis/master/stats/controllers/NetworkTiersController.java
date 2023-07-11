package dev.babebbu.academic.thesis.master.stats.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.babebbu.academic.thesis.master.stats.models.entities.NetworkTier;
import dev.babebbu.academic.thesis.master.stats.models.requests.NetworkTierRequest;
import dev.babebbu.academic.thesis.master.stats.repositories.TiersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("network-tiers")
@RequiredArgsConstructor
public class NetworkTiersController {

    private final TiersRepository tiersRepository;
    private final ObjectMapper objectMapper;

    @GetMapping
    public Object listNetworkTiers() {
        return tiersRepository.findAll(Pageable.unpaged());
    }

    @GetMapping("/{slug}")
    public Object getNetworkTier(@PathVariable("slug") final String slug) {
        return tiersRepository.findById(slug);
    }

    @PostMapping
    public Object createNetworkTier(@RequestBody NetworkTierRequest request) {
        NetworkTier networkTier = getEntityFromRequest(request);
        networkTier.setId(request.getName().toLowerCase().replace(" ", "-"));
        return tiersRepository.save(networkTier);
    }

    @PutMapping("/{slug}")
    public Object updateNetworkTier(@PathVariable("slug") String slug, @RequestBody NetworkTierRequest request) {
        Optional<NetworkTier> record = tiersRepository.findById(slug);

        if (record.isEmpty()) {
            return networkTierNotFoundError();
        }

        NetworkTier networkTier = getEntityFromRequest(request);
        networkTier.setId(slug);

        return tiersRepository.save(networkTier);
    }

    private NetworkTier getEntityFromRequest(NetworkTierRequest request) {
        return objectMapper.convertValue(request, NetworkTier.class);
    }

    @DeleteMapping("{slug}")
    public Object deleteNetworkTier(@PathVariable("slug") String slug) {
        if (!tiersRepository.existsById(slug)) {
            return networkTierNotFoundError();
        }
        tiersRepository.deleteById(slug);
        return ResponseEntity.ok();
    }

    private Object networkTierNotFoundError() {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "Network Tier not found"));
    }

}
