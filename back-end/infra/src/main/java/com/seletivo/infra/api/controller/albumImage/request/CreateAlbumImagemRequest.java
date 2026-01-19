package com.seletivo.infra.api.controller.albumImage.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public record CreateAlbumImagemRequest(
        @JsonProperty("album_id") UUID album_id,
        @JsonProperty("files") List<MultipartFile> files
) {
    public UUID albumId() {
        return album_id;
    }
}
