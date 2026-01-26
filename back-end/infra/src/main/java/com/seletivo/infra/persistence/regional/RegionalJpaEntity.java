package com.seletivo.infra.persistence.regional;

import com.seletivo.domain.regional.Regional;
import com.seletivo.domain.regional.RegionalID;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity(name = "Regional")
@Table(name = "regional")
public class RegionalJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regional_seq")
    @SequenceGenerator(name = "regional_seq", sequenceName = "seq_regional", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "secure_id", nullable = false, unique = true, updatable = false)
    private UUID secureId;

    @Column(name = "external_id", nullable = false, updatable = false)
    private Long externalId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public RegionalJpaEntity() {}

    private RegionalJpaEntity(
            final Long id,
            final UUID secureId,
            final Long externalId,
            final String nome,
            final boolean ativo,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.secureId = secureId;
        this.externalId = externalId;
        this.nome = nome;
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static RegionalJpaEntity from(final Regional aRegional) {
        return new RegionalJpaEntity(
                aRegional.getId() != null ? aRegional.getId().getValue() : null,
                aRegional.getSecureId(),
                aRegional.getExternalId(),
                aRegional.getNome(),
                aRegional.isAtivo(),
                aRegional.getCreatedAt(),
                aRegional.getUpdatedAt()
        );
    }

    public Regional toAggregate() {
        return Regional.with(
                this.id != null ? RegionalID.from(this.id, this.secureId.toString()) : null,
                this.secureId,
                this.externalId,
                this.nome,
                this.ativo,
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

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}