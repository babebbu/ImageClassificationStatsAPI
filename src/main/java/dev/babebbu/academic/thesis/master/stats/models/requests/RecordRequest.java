package dev.babebbu.academic.thesis.master.stats.models.requests;

import lombok.Data;

@Data
public class RecordRequest {

    private String device;

    private double inferenceTime;
    private double executionTime;

    private String imageFileName;
    private String prediction;
    private String actual;
    private boolean accurate;
    private double confidence;
    private double dataTransfer;

}
