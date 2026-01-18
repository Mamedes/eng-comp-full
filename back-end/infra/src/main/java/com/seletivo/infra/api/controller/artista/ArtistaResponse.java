package com.seletivo.infra.api.controller.artista;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ArtistaResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("secure_id") UUID secureId,
        @JsonProperty("nome") String nome,
        @JsonProperty("tipo") String tipo
) {
}