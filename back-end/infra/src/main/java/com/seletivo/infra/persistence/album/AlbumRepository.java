package com.seletivo.infra.persistence.album;

import com.seletivo.infra.persistence.album.projection.AlbumArtistasProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<AlbumJpaEntity, Long> {

    Optional<AlbumJpaEntity> findBySecureId(UUID secureId);

    void deleteBySecureId(UUID secureId);

    @Query(value = """
        SELECT 
            al.secure_id AS albumSecureId, 
            al.titulo AS albumTitulo, 
            art.nome AS artistaNome, 
            art.tipo AS artistaTipo
        FROM album al
        INNER JOIN album_artista aa ON al.id = aa.album_id
        INNER JOIN artista art ON aa.artista_id = art.id
        WHERE (:query IS NULL 
           OR al.titulo ILIKE %:query% 
           OR art.nome ILIKE %:query%)
    """,
            countQuery = """
        SELECT count(*) FROM album al
        INNER JOIN album_artista aa ON al.id = aa.album_id
        INNER JOIN artista art ON aa.artista_id = art.id
        WHERE (:query IS NULL OR al.titulo ILIKE %:query% OR art.nome ILIKE %:query%)
    """,
            nativeQuery = true)
    Page<AlbumArtistasProjection> findAllWithArtistas(
            @Param("query") String query,
            Pageable pageable
    );


}