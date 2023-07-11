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
@Table(name = "devices")
public class Device {

    @Id
    private String id;
    private String name;
    private String displayName;
    private String description;
    private String hardware;
    private String location;
    private double latency;

    @ManyToOne
    @JoinColumn
    private MLEnvironment environment;

}
