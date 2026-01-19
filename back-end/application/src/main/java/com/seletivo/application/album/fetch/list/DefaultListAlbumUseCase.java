package com.seletivo.application.album.fetch.list;


import com.seletivo.application.album.fetch.AlbumListOutput;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import java.util.Objects;

public class DefaultListAlbumUseCase extends ListAlbumUseCase {

    private final AlbumGateway albumGateway;
    private final ArtistaGateway artistaGateway;

    public DefaultListAlbumUseCase(final AlbumGateway albumGateway, final ArtistaGateway artistaGateway) {
        this.albumGateway = Objects.requireNonNull(albumGateway);
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public Pagination<AlbumListOutput> execute(final SearchQuery aQuery) {
        final var albums = this.albumGateway.findAll(aQuery);

        return albums.map(album -> {
            final var artistaUUID = this.artistaGateway.findById(ArtistaID.from(album.getArtistaID(), null))
                    .map(artista -> artista.getSecureId())
                    .orElse(null);

            return AlbumListOutput.from(album, artistaUUID);
        });
    }
}