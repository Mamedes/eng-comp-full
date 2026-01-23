package com.seletivo.infra.api.controller.artista;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ArtistaListResponse(
     // @JsonProperty("id") Long id,
        @JsonProperty("artistaId") UUID artistaId,
        @JsonProperty("nome") String nome,
        @JsonProperty("tipo") String tipo
) {
}
