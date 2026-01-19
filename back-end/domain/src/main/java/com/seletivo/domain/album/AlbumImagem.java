package com.seletivo.domain.album;

import com.seletivo.domain.AggregateRoot;
import com.seletivo.domain.validation.ValidationHandler;
import java.time.Instant;
import java.util.UUID;

public class AlbumImagem extends AggregateRoot<AlbumImagemID> {
    private UUID secureId;
    private Long albumId;
    private String fileName;
    private String fileKey;
    private String contentType;
    private Instant createdAt;

    private AlbumImagem(
            final AlbumImagemID id,
            final UUID secureId,
            final Long albumId,
            final String fileName,
            final String fileKey,
            final String contentType,
            final Instant createdAt
    ) {
        super(id);
        this.secureId = secureId;
        this.albumId = albumId;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.contentType = contentType;
        this.createdAt = createdAt;
    }

    public static AlbumImagem newAlbumImagem(
            final Long albumId,
            final String fileName,
            final String fileKey,
            final String contentType
    ) {
        final var now = Instant.now();
        return new AlbumImagem(null, UUID.randomUUID(), albumId, fileName, fileKey, contentType, now);
    }

    public static AlbumImagem with(
            final AlbumImagemID id,
            final UUID secureId,
            final Long albumId,
            final String fileName,
            final String fileKey,
            final String contentType,
            final Instant createdAt
    ) {
        return new AlbumImagem(id, secureId, albumId, fileName, fileKey, contentType, createdAt);
    }

    @Override
    public void validate(final ValidationHandler handler) {
    }

    public UUID getSecureId() {
        return secureId;
    }

    public void setSecureId(UUID secureId) {
        this.secureId = secureId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
