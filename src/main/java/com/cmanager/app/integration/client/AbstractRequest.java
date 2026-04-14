package com.cmanager.app.integration.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AbstractRequest<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractRequest.class);

    private final RestTemplate restTemplate;

    public AbstractRequest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "tvmaze", fallbackMethod = "fallback")
    public T getShow(String url, ParameterizedTypeReference<T> typeReference) {
        return restTemplate.exchange(url, HttpMethod.GET, null, typeReference).getBody();
    }

    public T fallback(String url, ParameterizedTypeReference<T> typeReference, Exception ex) {
        log.error("TVMaze unavailable after retries. URL: {} | Cause: {}", url, ex.getMessage());
        throw new RuntimeException("TVMaze service unavailable. Please try again later.", ex);
    }
}
