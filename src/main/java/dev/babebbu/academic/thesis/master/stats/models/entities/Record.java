package dev.babebbu.academic.thesis.master.stats.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn
    private Device device;

    private double inferenceTime;
    private double executionTime;

    private String imageFileName;
    private String prediction;
    private String actual;
    private boolean accurate;
    private double confidence;
    private double dataTransfer;

}
