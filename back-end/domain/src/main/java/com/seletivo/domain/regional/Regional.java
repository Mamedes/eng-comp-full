package com.seletivo.domain.regional;

import com.seletivo.domain.AggregateRoot;
import com.seletivo.domain.validation.ValidationHandler;
import java.time.Instant;
import java.util.UUID;

public class Regional extends AggregateRoot<RegionalID> {
    private final UUID secureId;
    private final Long externalId;
    private final String nome;
    private final boolean ativo;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Regional(
            final RegionalID id,
            final UUID secureId,
            final Long externalId,
            final String nome,
            final boolean ativo,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(id);
        this.secureId = secureId;
        this.externalId = externalId;
        this.nome = nome;
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Regional newRegional(final Long externalId, final String nome) {
        final var now = Instant.now();
        return new Regional(null, UUID.randomUUID(), externalId, nome, true, now, now);
    }

    public static Regional with(
            final RegionalID id,
            final UUID secureId,
            final Long externalId,
            final String nome,
            final boolean ativo,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Regional(id, secureId, externalId, nome, ativo, createdAt, updatedAt);
    }

    public Regional inativar() {
        return new Regional(
                this.id,
                this.secureId,
                this.externalId,
                this.nome,
                false,
                this.createdAt,
                Instant.now()
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new RegionalValidator(this, handler).validate();
    }

    public UUID getSecureId() { return secureId; }
    public Long getExternalId() { return externalId; }
    public String getNome() { return nome; }
    public boolean isAtivo() { return ativo; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}