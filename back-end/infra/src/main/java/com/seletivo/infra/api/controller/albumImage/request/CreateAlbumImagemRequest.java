package com.seletivo.infra.api.controller.albumImage.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public record CreateAlbumImagemRequest(
        @JsonProperty("albumId") UUID albumId,
        @JsonProperty("files") List<MultipartFile> files
) {
    public UUID albumId() {
        return albumId;
    }
}
