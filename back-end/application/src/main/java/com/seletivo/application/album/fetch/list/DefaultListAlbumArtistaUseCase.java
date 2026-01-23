package com.seletivo.application.album.fetch.list;


import com.seletivo.application.album.fetch.AlbumArtistaListView;
import com.seletivo.domain.album.AlbumArtistaQueryGateway;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DefaultListAlbumArtistaUseCase extends ListAlbumArtistaUseCase {

    private final AlbumArtistaQueryGateway albumArtistaQueryGateway;

    public DefaultListAlbumArtistaUseCase(final AlbumArtistaQueryGateway albumArtistaQueryGateway) {
        this.albumArtistaQueryGateway = Objects.requireNonNull(albumArtistaQueryGateway);
    }

    @Override
    public Pagination<AlbumArtistaListView> execute(final SearchQuery aQuery) {
        return this.albumArtistaQueryGateway.findAll(aQuery);
    }
}