package com.seletivo.infra.persistence.artista;

import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaID;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Artista")
@Table(name = "artista")
public class ArtistaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artista_seq")
    @SequenceGenerator(name = "artista_seq", sequenceName = "seq_artista", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "secure_id", nullable = false, unique = true, updatable = false)
    private UUID secureId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public ArtistaJpaEntity() {}

    public ArtistaJpaEntity(
            final Long id,
            final UUID secureId,
            final String nome,
            final String tipo,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.secureId = secureId;
        this.nome = nome;
        this.tipo = tipo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ArtistaJpaEntity from(final Artista aArtista) {
        return new ArtistaJpaEntity(
                aArtista.getId() != null ? aArtista.getId().getValue() : null,
                aArtista.getSecureId(),
                aArtista.getNome(),
                aArtista.getTipo(),
                aArtista.getCreatedAt(),
                aArtista.getUpdatedAt()
        );
    }

    public Artista toAggregate() {
        return Artista.with(
                this.id != null ? ArtistaID.from(this.id) : null,
                this.secureId,
                this.nome,
                this.tipo,
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArtistaJpaEntity that = (ArtistaJpaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(secureId, that.secureId) && Objects.equals(nome, that.nome) && Objects.equals(tipo, that.tipo) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secureId, nome, tipo, createdAt, updatedAt);
    }
}