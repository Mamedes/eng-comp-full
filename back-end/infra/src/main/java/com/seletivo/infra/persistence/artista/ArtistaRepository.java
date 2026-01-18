package com.seletivo.infra.persistence.artista;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ArtistaRepository extends JpaRepository<ArtistaJpaEntity, Long> {

    Optional<ArtistaJpaEntity> findBySecureId(UUID secureId);
    void deleteBySecureId(UUID secureId);
}