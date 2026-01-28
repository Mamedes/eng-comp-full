package com.seletivo.infra.api.controller.artista.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ArtistaDetalheResponse(
        @JsonProperty("artistaId") UUID id,
        @JsonProperty("nome") String nome,
        @JsonProperty("tipo") String tipo,
        @JsonProperty("createdAt") Instant createdAt,
        @JsonProperty("updatedAt") Instant updatedAt,
        @JsonProperty("albuns") List<AlbumDetalheResponse> albuns
) {}