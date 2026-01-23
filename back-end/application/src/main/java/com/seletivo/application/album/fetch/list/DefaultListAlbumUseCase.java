package com.seletivo.application.album.fetch.list;

import com.seletivo.application.album.fetch.AlbumListOutput;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
            final Set<UUID> artistasUUIDs = album.getArtistas().stream()
                    .map(artistaId -> this.artistaGateway.findById(artistaId)
                            .map(Artista::getSecureId)
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            return AlbumListOutput.from(album, artistasUUIDs);
        });
    }
}