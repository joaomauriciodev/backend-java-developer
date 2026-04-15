package com.cmanager.app.integration.service;

import com.cmanager.app.integration.client.AbstractRequest;
import com.cmanager.app.integration.dto.ShowsRequestDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    private static final String URL = "https://api.tvmaze.com/singlesearch/shows?q=%s&embed=episodes";

    private final AbstractRequest<ShowsRequestDTO> abstractConnect;

    public RequestService(AbstractRequest<ShowsRequestDTO> abstractConnect) {
        this.abstractConnect = abstractConnect;
    }

    public ShowsRequestDTO getShow(String showName) {
        final var url = String.format(URL, showName);
        return abstractConnect.getShow(url, new ParameterizedTypeReference<>() {
        });
    }
}
