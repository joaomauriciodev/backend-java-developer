package com.cmanager.app.application.repository;

import com.cmanager.app.application.domain.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, String> {

    boolean existsByIdIntegration(Integer idIntegration);

    Optional<Episode> findByIdIntegration(Integer idIntegration);

    List<Episode> findByFkShowIn(List<String> showIds);
}
