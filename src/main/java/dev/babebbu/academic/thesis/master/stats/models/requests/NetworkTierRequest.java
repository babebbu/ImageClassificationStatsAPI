package dev.babebbu.academic.thesis.master.stats.models.requests;

import lombok.Data;

@Data
public class NetworkTierRequest {
    private String name;
    private String type;
    private String tier;
    private String description;

}
