package com.seletivo.domain.album;

import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;

public interface AlbumArtistaQueryGateway<T> {
    Pagination<T> findAll(SearchQuery query);
}

