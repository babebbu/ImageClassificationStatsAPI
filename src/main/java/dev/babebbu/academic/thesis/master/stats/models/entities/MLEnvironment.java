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
@Table(name = "ml_environments")
public class MLEnvironment {

    @Id
    private String id;

    private String name;

    @ManyToOne
    private NetworkTier tier;

    @ManyToOne
    @JoinColumn
    private Runtime runtime;

    @ManyToOne
    @JoinColumn
    private ApplicationType applicationType;

}
