package com.cmanager.app.application.service;

import com.cmanager.app.application.data.ShowDTO;
import com.cmanager.app.application.repository.ShowRepository;
import com.cmanager.app.core.data.PageResultResponse;
import com.cmanager.app.core.utils.Util;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public PageResultResponse<ShowDTO> list(String name, int page, int size, String sortField, String sortOrder) {
        final var pageable = Util.getPageable(page, size, sortField, sortOrder);
        final var result = showRepository.findByNameContainingIgnoreCase(name, pageable);
        final var pageImpl = new PageImpl<>(
                result.stream().map(ShowDTO::convertEntity).toList(),
                pageable,
                result.getTotalElements()
        );
        return PageResultResponse.from(pageImpl);
    }
}
