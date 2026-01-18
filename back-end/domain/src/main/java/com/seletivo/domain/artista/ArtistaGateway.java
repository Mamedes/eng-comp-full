package com.seletivo.domain.artista;

import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import java.util.Optional;
import java.util.UUID;

public interface ArtistaGateway {
    Artista create(Artista anArtista);
    void deleteBySecureId(UUID secureId);
    Optional<Artista> findById(ArtistaID anId);
    Optional<Artista> findBySecureId(UUID secureId);
    Artista update(Artista anArtista);
    Pagination<Artista> findAll(SearchQuery aQuery);
}