package com.seletivo.domain.artista;

import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import java.util.Optional;

public interface ArtistaGateway {
    Artista create(Artista anArtista);
    void deleteById(ArtistaID anId);
    Optional<Artista> findById(ArtistaID anId);
    Artista update(Artista anArtista);
    Pagination<Artista> findAll(SearchQuery aQuery);
}