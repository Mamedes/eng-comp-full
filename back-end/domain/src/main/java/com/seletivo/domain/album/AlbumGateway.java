package com.seletivo.domain.album;

import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import java.util.Optional;
import java.util.UUID;

public interface AlbumGateway {

    Album create(Album anAlbum);

    void deleteBySecureId(UUID secureId);

    Optional<Album> findById(AlbumID anId);

    Optional<Album> findBySecureId(UUID secureId);

    Album update(Album anAlbum);

    Pagination<Album> findAll(SearchQuery aQuery);
}