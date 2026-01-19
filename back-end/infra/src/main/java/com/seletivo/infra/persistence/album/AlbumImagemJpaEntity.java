package com.seletivo.infra.persistence.album;

import com.seletivo.domain.album.AlbumImagem;
import com.seletivo.domain.album.AlbumImagemID;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity(name = "AlbumImagem")
@Table(name = "album_imagem")
public class AlbumImagemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_album_imagem")
    @SequenceGenerator(name = "seq_album_imagem", sequenceName = "seq_album_imagem", allocationSize = 1)
    private Long id;

    @Column(name = "secure_id", unique = true, updatable = false)
    private UUID secureId;

    @Column(name = "album_id", nullable = false)
    private Long albumId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_key", nullable = false, length = 512)
    private String fileKey;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public AlbumImagemJpaEntity() {}

    private AlbumImagemJpaEntity(
            final Long id,
            final UUID secureId,
            final Long albumId,
            final String fileName,
            final String fileKey,
            final String contentType,
            final Instant createdAt
    ) {
        this.id = id;
        this.secureId = secureId;
        this.albumId = albumId;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.contentType = contentType;
        this.createdAt = createdAt;
    }

    public static AlbumImagemJpaEntity from(final AlbumImagem aDomain) {
        return new AlbumImagemJpaEntity(
                aDomain.getId() == null ? null : aDomain.getId().getValue(),
                aDomain.getSecureId(),
                aDomain.getAlbumId(),
                aDomain.getFileName(),
                aDomain.getFileKey(),
                aDomain.getContentType(),
                aDomain.getCreatedAt()
        );
    }

    public AlbumImagem toAggregate() {
        return AlbumImagem.with(
                this.id != null ? AlbumImagemID.from(this.id, this.secureId.toString()) : null,
                secureId,
                albumId,
                fileName,
                fileKey,
                contentType,
                createdAt
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
