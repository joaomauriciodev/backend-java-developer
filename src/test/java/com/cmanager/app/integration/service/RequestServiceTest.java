package com.cmanager.app.integration.service;

import com.cmanager.app.integration.client.AbstractRequest;
import com.cmanager.app.integration.dto.RatingDTO;
import com.cmanager.app.integration.dto.ShowsRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

        @Mock
        private AbstractRequest<ShowsRequestDTO> abstractConnect;

        @InjectMocks
        private RequestService requestService;

        @Test
        void shouldReturnFriendsShowSuccessfully() {
                // Arrange
                String showName = "friends";
                String expectedUrl = "https://api.tvmaze.com/singlesearch/shows?q=friends&embed=episodes";

                ShowsRequestDTO expectedResponse = new ShowsRequestDTO(
                                431,
                                "Friends",
                                "Scripted",
                                "English",
                                "Ended",
                                30,
                                30,
                                null,
                                new RatingDTO(BigDecimal.valueOf(8.5)),
                                "Sitcom clássico",
                                null);

                when(abstractConnect.getShow(
                                eq(expectedUrl),
                                any(ParameterizedTypeReference.class))).thenReturn(expectedResponse);

                // Act
                ShowsRequestDTO result = requestService.getShow(showName);

                // Assert
                assertNotNull(result);
                assertEquals("Friends", result.name());

                verify(abstractConnect, times(1))
                                .getShow(eq(expectedUrl), any(ParameterizedTypeReference.class));
        }

        @Test
        void shouldThrowExceptionWhenApiFails() {
                // Arrange
                when(abstractConnect.getShow(anyString(), any()))
                                .thenThrow(new RuntimeException("API Error"));

                // Act + Assert
                RuntimeException exception = assertThrows(
                                RuntimeException.class,
                                () -> requestService.getShow("friends"));

                assertEquals("API Error", exception.getMessage());
        }

        @Test
        void shouldBuildCorrectUrl() {
                // Arrange
                String showName = "friends";

                when(abstractConnect.getShow(anyString(), any()))
                                .thenReturn(null);

                // Act
                requestService.getShow(showName);

                // Assert
                verify(abstractConnect).getShow(
                                eq("https://api.tvmaze.com/singlesearch/shows?q=friends&embed=episodes"),
                                any());
        }
}