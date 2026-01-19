package com.seletivo.infra.persistence.album;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlbumImagemRepository extends JpaRepository<AlbumImagemJpaEntity, Long> {

    Optional<AlbumImagemJpaEntity> findBySecureId(UUID secureId);

    List<AlbumImagemJpaEntity> findAllByAlbumId(Long albumId);

    @Query("select a.id from AlbumImagem a where a.id in :ids")
    List<Long> existsByIds(@Param("ids") List<Long> ids);
}
