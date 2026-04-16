package com.cmanager.app.application.service;

import com.cmanager.app.application.data.SeasonAverageDTO;
import com.cmanager.app.application.repository.EpisodeRepository;
import com.cmanager.app.core.data.PageResultResponse;
import com.cmanager.app.core.utils.Util;
import org.springframework.stereotype.Service;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public PageResultResponse<SeasonAverageDTO> averagePerSeason(String showName, int page, int size, String sortField, String sortOrder) {
        final var pageable = Util.getPageable(page, size, sortField, sortOrder);
        return PageResultResponse.from(episodeRepository.findAveragePerSeason(showName, pageable));
    }
}
