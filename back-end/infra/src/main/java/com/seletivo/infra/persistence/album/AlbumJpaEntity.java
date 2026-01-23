package com.seletivo.infra.persistence.album;

import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumID;
import com.seletivo.domain.artista.ArtistaID;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @ElementCollection
    @CollectionTable(name = "album_artista", joinColumns = @JoinColumn(name = "album_id"))
    @Column(name = "artista_id")
    private Set<Long> artistaIds;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public AlbumJpaEntity() {
    }

    private AlbumJpaEntity(
            final Long id,
            final UUID secureId,
            final String titulo,
            final Set<Long> artistaIds,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.secureId = secureId;
        this.titulo = titulo;
        this.artistaIds = artistaIds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AlbumJpaEntity from(final Album anAlbum) {
        return new AlbumJpaEntity(
                anAlbum.getId() != null ? anAlbum.getId().getValue() : null,
                anAlbum.getSecureId(),
                anAlbum.getTitulo(),
                anAlbum.getArtistas().stream().map(ArtistaID::getValue).collect(Collectors.toSet()),
                anAlbum.getCreatedAt(),
                anAlbum.getUpdatedAt()
        );
    }
    public Album toAggregate() {
        return Album.with(
                AlbumID.from(this.id, this.secureId.toString()),
                this.secureId,
                this.titulo,
                this.artistaIds.stream().map(id -> ArtistaID.from(id, null)).collect(Collectors.toSet()),
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

    public Set<Long> getArtistaIds() {
        return artistaIds;
    }

    public void setArtistaIds(Set<Long> artistaIds) {
        this.artistaIds = artistaIds;
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