package com.cmanager.app.application.repository;

import com.cmanager.app.application.data.SeasonAverageDTO;
import com.cmanager.app.application.domain.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, String> {

    boolean existsByIdIntegration(Integer idIntegration);

    Optional<Episode> findByIdIntegration(Integer idIntegration);

    List<Episode> findByFkShowIn(List<String> showIds);

    @Query(
        value = """
            SELECT new com.cmanager.app.application.data.SeasonAverageDTO(
                s.id, s.name, e.season,
                cast(avg(e.rating) as BigDecimal)
            )
            FROM Episode e
            JOIN Show s ON s.id = e.fkShow
            WHERE :showName = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :showName, '%'))
            GROUP BY s.id, s.name, e.season
            """,
        countQuery = """
            SELECT COUNT(DISTINCT CONCAT(e.fkShow, cast(e.season as String)))
            FROM Episode e
            JOIN Show s ON s.id = e.fkShow
            WHERE :showName = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :showName, '%'))
            """
    )
    Page<SeasonAverageDTO> findAveragePerSeason(@Param("showName") String showName, Pageable pageable);
}
