package com.seletivo.application.album.fetch.get;

import com.seletivo.application.album.fetch.AlbumOutput;
import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DefaultGetAlbumByIdUseCase extends GetAlbumByIdUseCase {

    private final AlbumGateway albumGateway;
    private final ArtistaGateway artistaGateway;

    public DefaultGetAlbumByIdUseCase(final AlbumGateway albumGateway, final ArtistaGateway artistaGateway) {
        this.albumGateway = Objects.requireNonNull(albumGateway);
        this.artistaGateway = artistaGateway;
    }

    @Override
    public AlbumOutput execute(final UUID aSecureId) {
        final Album album = this.albumGateway.findBySecureId(aSecureId)
                .orElseThrow(notFound(aSecureId));

        final Set<UUID> artistasSecureIds = album.getArtistas().stream()
                .map(artistaId -> artistaGateway.findById(artistaId)
                        .map(Artista::getSecureId)
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return AlbumOutput.from(album, artistasSecureIds);
    }

    private Supplier<NotFoundException> notFound(final UUID aSecureId) {
        return () -> NotFoundException.with(Album.class, aSecureId);
    }
}


