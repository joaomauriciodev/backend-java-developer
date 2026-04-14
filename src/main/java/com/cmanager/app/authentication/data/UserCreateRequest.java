package com.cmanager.app.authentication.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "UserCreateRequest", description = "Payload para criação de um usuário")
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserCreateRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        @Schema(name = "username", description = "Login para acesso")
        String username,
        @Schema(name = "password", description = "Senha para acesso")
        @NotBlank
        @Size(min = 6, max = 200)
        String password,   
        @NotNull
        @Schema(name = "enabled", description = "Habilitado true ou false")
        boolean enabled
) {
}
