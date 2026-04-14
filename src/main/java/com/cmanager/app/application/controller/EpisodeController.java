package com.cmanager.app.application.controller;

import com.cmanager.app.application.data.SeasonAverageDTO;
import com.cmanager.app.application.service.EpisodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/episodes")
@Tag(name = "EpisodeController", description = "API de episódios")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @Operation(summary = "averagePerSeason", description = "Retorna a nota média de rating por temporada", responses = {
            @ApiResponse(responseCode = "200", description = "Médias calculadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum episódio encontrado")
    })
    @GetMapping("/average")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<SeasonAverageDTO>> averagePerSeason() {
        return ResponseEntity.ok(episodeService.averagePerSeason());
    }
}
