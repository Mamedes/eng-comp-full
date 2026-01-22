package com.seletivo.infra.persistence.artista;

import com.seletivo.infra.persistence.artista.projection.ArtistaListViewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ArtistaRepository extends JpaRepository<ArtistaJpaEntity, Long> {

    Optional<ArtistaJpaEntity> findBySecureId(UUID secureId);
    void deleteBySecureId(UUID secureId);

    @Query(value = """
        SELECT
            a.secure_id AS secureId,
            a.nome AS nome,
            a.tipo AS tipo,
            COUNT(al.id) AS quantidadeAlbuns
        FROM artista a
        LEFT JOIN album al ON a.id = al.artista_id
        WHERE (:nome IS NULL OR a.nome ILIKE %:nome%)
        GROUP BY a.id, a.secure_id, a.nome, a.tipo
    """,
            countQuery = """
        SELECT count(*) FROM artista a WHERE (:nome IS NULL OR a.nome ILIKE %:nome%)
    """,
            nativeQuery = true)
    Page<ArtistaListViewProjection> findAllWithAlbumCount(
            @Param("nome") String nome,
            Pageable pageable
    );
}