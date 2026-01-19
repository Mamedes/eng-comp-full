package com.seletivo.infra.persistence.album;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<AlbumJpaEntity, Long> {

    Optional<AlbumJpaEntity> findBySecureId(UUID secureId);

    void deleteBySecureId(UUID secureId);
}