package com.cmanager.app.application.service;

import com.cmanager.app.application.data.ShowCreateRequest;
import com.cmanager.app.application.data.ShowDTO;
import com.cmanager.app.application.domain.Episode;
import com.cmanager.app.application.domain.Show;
import com.cmanager.app.application.repository.EpisodeRepository;
import com.cmanager.app.application.repository.ShowRepository;
import com.cmanager.app.core.exception.AlreadyExistsException;
import com.cmanager.app.integration.client.RequestService;
import com.cmanager.app.integration.dto.EpisodeRequestDTO;
import com.cmanager.app.integration.dto.ShowsRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SyncShowService {

    private final RequestService requestService;
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;

    public SyncShowService(RequestService requestService,
                           ShowRepository showRepository,
                           EpisodeRepository episodeRepository) {
        this.requestService = requestService;
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
    }

    @Transactional
    public ShowDTO sync(ShowCreateRequest request) {
        final ShowsRequestDTO external = requestService.getShow(request.name());

        if (showRepository.findByIdIntegration(external.id()).isPresent()) {
            throw new AlreadyExistsException("Show", external.name());
        }

        final Show show = toShow(external);
        showRepository.save(show);

        final List<EpisodeRequestDTO> episodes = external._embedded() != null
                ? external._embedded().episodes()
                : List.of();

        for (EpisodeRequestDTO ep : episodes) {
            if (!episodeRepository.existsByIdIntegration(ep.id())) {
                episodeRepository.save(toEpisode(ep, show.getId()));
            }
        }

        return ShowDTO.convertEntity(show);
    }

    private Show toShow(ShowsRequestDTO dto) {
        final Show show = new Show();
        show.setIdIntegration(dto.id());
        show.setName(dto.name());
        show.setType(dto.type());
        show.setLanguage(dto.language());
        show.setStatus(dto.status());
        show.setRuntime(dto.runtime());
        show.setAverageRuntime(dto.averageRuntime());
        show.setOfficialSite(dto.officialSite());
        show.setRating(dto.rating() != null ? dto.rating().average() : null);
        show.setSummary(dto.summary());
        return show;
    }

    private Episode toEpisode(EpisodeRequestDTO dto, String showId) {
        final Episode episode = new Episode();
        episode.setIdIntegration(dto.id());
        episode.setFkShow(showId);
        episode.setName(dto.name());
        episode.setSeason(dto.season());
        episode.setNumber(dto.number());
        episode.setType(dto.type());
        episode.setAirdate(dto.airdate());
        episode.setAirtime(dto.airtime());
        episode.setAirstamp(dto.airstamp() != null ? dto.airstamp().toString() : null);
        episode.setRuntime(dto.runtime());
        episode.setRating(dto.rating() != null ? dto.rating().average() : null);
        episode.setSummary(dto.summary());
        return episode;
    }
}
