package com.seletivo.domain.album;


import com.seletivo.domain.AggregateRoot;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.validation.ValidationHandler;
import java.time.Instant;
import java.util.UUID;

public class Album extends AggregateRoot<AlbumID> implements Cloneable{
    private final UUID secureId;
    private String titulo;
    private final  ArtistaID artistaID;
    private Instant createdAt;
    private Instant updatedAt;

    private Album(
            final AlbumID id,
            final UUID secureId,
            final String titulo,
            final  ArtistaID artistaID,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(id);
        this.secureId = secureId;
        this.titulo = titulo;
        this.artistaID = artistaID;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Album newAlbum(final String titulo, final ArtistaID artistaID){
        final var now = Instant.now();
        return  new Album(null, UUID.randomUUID(),titulo,artistaID, now, now);
    }

    public static Album with(
            final AlbumID id,
            final UUID secureId,
            final String titulo,
            final ArtistaID artistaId,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Album(id, secureId, titulo, artistaId, createdAt, updatedAt);
    }
    public static Album with(final Album aAlbum) {
        return with(aAlbum.id,aAlbum.secureId,aAlbum.titulo,aAlbum.artistaID,aAlbum.createdAt,aAlbum.updatedAt);
    }

    public Album update(final String titulo,final ArtistaID artistaId) {
        return new Album(
                this.id,
                this.secureId,
                titulo,
                artistaId,
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

    public ArtistaID getArtistaID() {
        return artistaID;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}