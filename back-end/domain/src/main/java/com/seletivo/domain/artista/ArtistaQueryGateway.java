package com.seletivo.domain.artista;

import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;

public interface ArtistaQueryGateway<T> {

    Pagination<T> findAll(SearchQuery query);
}
