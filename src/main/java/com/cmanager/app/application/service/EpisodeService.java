package com.cmanager.app.application.service;

import com.cmanager.app.application.data.SeasonAverageDTO;
import com.cmanager.app.application.domain.Episode;
import com.cmanager.app.application.domain.Show;
import com.cmanager.app.application.repository.EpisodeRepository;
import com.cmanager.app.application.repository.ShowRepository;
import com.cmanager.app.core.data.PageResultResponse;
import com.cmanager.app.core.utils.Util;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final ShowRepository showRepository;

    public EpisodeService(EpisodeRepository episodeRepository, ShowRepository showRepository) {
        this.episodeRepository = episodeRepository;
        this.showRepository = showRepository;
    }

    public PageResultResponse<SeasonAverageDTO> averagePerSeason(String showName, int page, int size, String sortField, String sortOrder) {
        final Map<String, Show> showsById = resolveShows(showName);

        final List<Episode> episodes = showsById.isEmpty()
                ? episodeRepository.findAll()
                : episodeRepository.findByFkShowIn(List.copyOf(showsById.keySet()));

        if (episodes.isEmpty()) {
            throw new EntityNotFoundException("No episodes found");
        }

        final List<SeasonAverageDTO> aggregated = episodes.stream()
                .collect(Collectors.groupingBy(e -> e.getFkShow() + "-" + e.getSeason()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    final Episode first = entry.getValue().get(0);
                    final Show show = showsById.get(first.getFkShow());

                    final List<BigDecimal> ratings = entry.getValue().stream()
                            .map(Episode::getRating)
                            .filter(r -> r != null)
                            .toList();

                    final BigDecimal average = ratings.isEmpty()
                            ? BigDecimal.ZERO
                            : ratings.stream()
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                                    .divide(BigDecimal.valueOf(ratings.size()), 2, RoundingMode.HALF_UP);

                    return new SeasonAverageDTO(
                            show != null ? show.getId() : first.getFkShow(),
                            show != null ? show.getName() : null,
                            first.getSeason(),
                            average
                    );
                })
                .toList();

        final var pageable = Util.getPageable(page, size, sortField, sortOrder);
        final int total = aggregated.size();
        final int from = Math.min(page * size, total);
        final int to = Math.min(from + size, total);

        return PageResultResponse.from(
                new PageImpl<>(aggregated.subList(from, to), pageable, total)
        );
    }

    private Map<String, Show> resolveShows(String showName) {
        final List<Show> shows = showName.isBlank()
                ? showRepository.findAll()
                : showRepository.findByNameContainingIgnoreCase(showName);
        return shows.stream().collect(Collectors.toMap(Show::getId, s -> s));
    }
}
