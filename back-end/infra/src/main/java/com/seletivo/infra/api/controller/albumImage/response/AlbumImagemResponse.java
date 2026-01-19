package com.seletivo.infra.api.controller.albumImage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.UUID;

public record AlbumImagemResponse(
       // @JsonProperty("id") Long id,
        @JsonProperty("secureId") UUID secureId,
     // @JsonProperty("albumId") Long albumId,
        @JsonProperty("fileName") String fileName,
        @JsonProperty("bucket") String bucket,
        @JsonProperty("contentType") String contentType,
        @JsonProperty("linkTemporario") String linkTemporario,
        @JsonProperty("createdAt") Instant createdAt
) {
}
