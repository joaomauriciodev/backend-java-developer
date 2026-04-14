package com.cmanager.app.application.controller;

import com.cmanager.app.application.data.SeasonAverageDTO;
import com.cmanager.app.application.service.EpisodeService;
import com.cmanager.app.core.data.PageResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<PageResultResponse<SeasonAverageDTO>> averagePerSeason(
            @Parameter(description = "Filtro por nome do show", example = "Breaking") @RequestParam(required = false, defaultValue = "") String showName,
            @Parameter(description = "Número da página (inicia em 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de registros por página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação", example = "season") @RequestParam(defaultValue = "season") String sortField,
            @Parameter(description = "Direção da ordenação (ASC ou DESC)", example = "ASC") @RequestParam(defaultValue = "ASC") String sortOrder) {
        return ResponseEntity.ok(episodeService.averagePerSeason(showName, page, size, sortField, sortOrder));
    }
}
