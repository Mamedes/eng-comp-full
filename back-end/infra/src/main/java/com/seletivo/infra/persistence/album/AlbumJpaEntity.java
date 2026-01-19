package com.seletivo.infra.persistence.album;

import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumID;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity(name = "Album")
@Table(name = "album")
public class AlbumJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_seq")
    @SequenceGenerator(name = "album_seq", sequenceName = "seq_album", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "secure_id", nullable = false, unique = true, updatable = false)
    private UUID secureId;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "artista_id", nullable = false)
    private Long artistaID;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public AlbumJpaEntity() {
    }

    public AlbumJpaEntity(
            final Long id,
            final UUID secureId,
            final String titulo,
            final Long artistaID,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.secureId = secureId;
        this.titulo = titulo;
        this.artistaID =artistaID;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AlbumJpaEntity from(final Album anAlbum) {
        return new AlbumJpaEntity(
                anAlbum.getId() != null ? anAlbum.getId().getValue() : null,
                anAlbum.getSecureId() != null ? anAlbum.getSecureId() : null,
                anAlbum.getTitulo(),
                anAlbum.getArtistaID(),
                anAlbum.getCreatedAt(),
                anAlbum.getUpdatedAt()

        );

    }
    public Album toAggregate() {
        return Album.with(
                getId() != null ? AlbumID.from(getId(), null) : null,
                this.secureId,
                this.titulo,
                getArtistaID(),
                this.createdAt,
                this.updatedAt
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getArtistaID() {
        return artistaID;
    }

    public void setArtistaID(Long artistaID) {
        this.artistaID = artistaID;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}