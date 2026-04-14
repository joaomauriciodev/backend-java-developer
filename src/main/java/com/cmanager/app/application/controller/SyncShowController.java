package com.cmanager.app.application.controller;

import com.cmanager.app.application.data.ShowCreateRequest;
import com.cmanager.app.application.data.ShowDTO;
import com.cmanager.app.application.service.SyncShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/shows")
@Tag(name = "SyncShowController", description = "API de sincronização de TV Shows")
public class SyncShowController {

    private final SyncShowService syncShowService;

    public SyncShowController(SyncShowService syncShowService) {
        this.syncShowService = syncShowService;
    }

    @Operation(summary = "sync", description = "Sincroniza um TV show via TVMaze", responses = {
            @ApiResponse(responseCode = "201", description = "Show sincronizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Show já existe")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowDTO> sync(@RequestBody @Valid ShowCreateRequest request) {
        final ShowDTO dto = syncShowService.sync(request);
        return ResponseEntity.created(URI.create("/api/shows/" + dto.id())).body(dto);
    }
}
