package com.seletivo.application.album.fetch.list;


import com.seletivo.application.UseCase;
import com.seletivo.application.album.fetch.AlbumArtistaListView;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;

public abstract class ListAlbumArtistaUseCase
        extends UseCase<SearchQuery, Pagination<AlbumArtistaListView>> {
}