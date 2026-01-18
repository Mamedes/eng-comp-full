package com.seletivo.domain.artista;

import com.seletivo.domain.AggregateRoot;
import com.seletivo.domain.validation.ValidationHandler;
import java.time.Instant;
import java.util.UUID;

public class Artista extends AggregateRoot<ArtistaID> {
    private final UUID secureId;
    private final String nome;
    private final String tipo;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Artista(
            final ArtistaID id,
            final UUID secureId,
            final String nome,
            final String tipo,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(id);
        this.secureId = secureId;
        this.nome = nome;
        this.tipo = tipo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Artista newArtista(final String nome, final String tipo) {
        final var now = Instant.now();
        return new Artista(null, UUID.randomUUID(), nome, tipo, now, now);
    }

    public static Artista with(
            final ArtistaID id,
            final UUID secureId,
            final String nome,
            final String tipo,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Artista(id, secureId, nome, tipo, createdAt, updatedAt);
    }

    public Artista update(final String nome, final String tipo) {
        return new Artista(
                this.id,
                this.secureId,
                nome,
                tipo,
                this.createdAt,
                Instant.now()
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new ArtistaValidator(this, handler).validate();
    }

    public UUID getSecureId() { return secureId; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}