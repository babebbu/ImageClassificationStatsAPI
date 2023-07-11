package dev.babebbu.academic.thesis.master.stats.repositories;

import dev.babebbu.academic.thesis.master.stats.models.entities.ApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTypesRepository extends JpaRepository<ApplicationType, String> {
}
