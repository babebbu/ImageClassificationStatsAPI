package dev.babebbu.academic.thesis.master.stats.models.requests;

import lombok.Data;

@Data
public class RuntimeRequest {
    private String os;
    private String name;
    private String mlEngine;
}
