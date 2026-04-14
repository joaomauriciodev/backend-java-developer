package com.cmanager.app.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "SeasonAverageDTO", description = "Nota média de rating por temporada")
public record SeasonAverageDTO(

        @JsonProperty("showId")
        @Schema(name = "showId", description = "Id do show")
        String showId,

        @JsonProperty("showName")
        @Schema(name = "showName", description = "Nome do show")
        String showName,

        @JsonProperty("season")
        @Schema(name = "season", description = "Número da temporada")
        Integer season,

        @JsonProperty("average")
        @Schema(name = "average", description = "Nota média dos episódios da temporada")
        BigDecimal average

) {
}
