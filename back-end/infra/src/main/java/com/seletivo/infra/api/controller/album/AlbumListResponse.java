package com.seletivo.infra.api.controller.album;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.UUID;

public record AlbumListResponse(
        @JsonProperty("albumId") UUID secureId,
        @JsonProperty("albumTitulo") String titulo,
        @JsonProperty("artistaIds") Set<UUID> artistasIds
) {
}