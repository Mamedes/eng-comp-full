package com.seletivo.domain.album;


import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface AlbumImagemGateway {

    AlbumImagem create(AlbumImagem anAlbumImagem);

    void deleteById(AlbumImagemID anId);

    Optional<AlbumImagem> findById(AlbumImagemID anId);

    AlbumImagem update(AlbumImagem anAlbumImagem);

    Pagination<AlbumImagem> findAll(SearchQuery aQuery);

    List<AlbumImagemID> existsByIds(Iterable<AlbumImagemID> ids);
}
