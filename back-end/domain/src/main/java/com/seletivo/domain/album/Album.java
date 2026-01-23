package com.seletivo.domain.album;


import com.seletivo.domain.AggregateRoot;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.validation.ValidationHandler;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Album extends AggregateRoot<AlbumID> implements Cloneable{
    private final UUID secureId;
    private String titulo;
    private final Set<ArtistaID> artistas;
    private Instant createdAt;
    private Instant updatedAt;

    private Album(
            final AlbumID id,
            final UUID secureId,
            final String titulo,
            final Set<ArtistaID>  artistas,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(id);
        this.secureId = secureId;
        this.titulo = titulo;
        this.artistas = artistas;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Album newAlbum(final String titulo, final Set<ArtistaID> artistas) {
        final var now = Instant.now();
        return new Album(null, UUID.randomUUID(), titulo, artistas, now, now);
    }
    public static Album with(
            final AlbumID id,
            final UUID secureId,
            final String titulo,
            final Set<ArtistaID> artistas,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Album(id, secureId, titulo, artistas, createdAt, updatedAt);
    }
    public static Album with(final Album aAlbum) {
        return with(aAlbum.id,aAlbum.secureId,aAlbum.titulo,aAlbum.artistas,aAlbum.createdAt,aAlbum.updatedAt);
    }

    public Album update(final String titulo,final Set<ArtistaID> artistas) {
        return new Album(
                this.id,
                this.secureId,
                titulo,
                artistas,
                this.createdAt,
                Instant.now()
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new AlbumValidator(this, handler).validate();
    }

    public UUID getSecureId() {
        return secureId;
    }

    public String getTitulo() {
        return titulo;
    }

    public Set<ArtistaID> getArtistas() {
        return artistas;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}