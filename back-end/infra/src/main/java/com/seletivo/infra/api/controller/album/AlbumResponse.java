package com.seletivo.infra.api.controller.album;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.UUID;

public record AlbumResponse(
        @JsonProperty("secure_id") UUID secureId,
        @JsonProperty("titulo") String titulo,
        @JsonProperty("artistas_ids") Set<UUID> artistasIds
) {}
