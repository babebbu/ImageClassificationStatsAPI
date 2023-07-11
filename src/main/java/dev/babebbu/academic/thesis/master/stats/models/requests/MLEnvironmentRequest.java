package dev.babebbu.academic.thesis.master.stats.models.requests;

import lombok.Data;

@Data
public class MLEnvironmentRequest {
    private String name;
    private String tier;
    private String runtime;
    private String applicationType;
}
