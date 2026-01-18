package com.seletivo.application.artista.fetch.list;

import com.seletivo.application.UseCase;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;

public abstract class ListArtistaUseCase
        extends UseCase<SearchQuery, Pagination<ArtistaListOutput>> {
}
