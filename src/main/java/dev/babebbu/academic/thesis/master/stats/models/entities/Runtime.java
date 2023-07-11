package dev.babebbu.academic.thesis.master.stats.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "runtimes")
public class Runtime {
    @Id
    private String id;
    private String name;
    private String os;
    private String mlEngine;
}
