package com.cmanager.app.application.controller;

import com.cmanager.app.application.data.ShowDTO;
import com.cmanager.app.application.service.ShowService;
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
@RequestMapping("/api/shows")
@Tag(name = "ShowController", description = "API de consulta de TV Shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @Operation(summary = "list", description = "Lista TV shows com paginação e filtro por nome", responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<PageResultResponse<ShowDTO>> list(
            @Parameter(description = "Filtro por nome", example = "Breaking") @RequestParam(required = false, defaultValue = "") String name,
            @Parameter(description = "Número da página (inicia em 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de registros por página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação", example = "name") @RequestParam(defaultValue = "name") String sortField,
            @Parameter(description = "Direção da ordenação (ASC ou DESC)", example = "ASC") @RequestParam(defaultValue = "ASC") String sortOrder) {
        return ResponseEntity.ok(showService.list(name, page, size, sortField, sortOrder));
    }
}
