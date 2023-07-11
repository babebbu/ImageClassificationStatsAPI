package dev.babebbu.academic.thesis.master.stats.models.requests;

import lombok.Data;

@Data
public class DeviceRequest {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private String hardware;
    private String location;
    private double latency;
    private String environment;
}
