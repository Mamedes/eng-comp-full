package com.seletivo.application.album.albumImage.fetch.list;

import com.seletivo.application.album.albumImage.AlbumImagemOutput;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.exceptions.NotFoundException;
import com.seletivo.domain.album.Album;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultListAlbumImagensByAlbumUseCase extends ListAlbumImagensByAlbumUseCase {

    private final AlbumImagemGateway albumImagemGateway;
    private final AlbumGateway albumGateway;

    public DefaultListAlbumImagensByAlbumUseCase(
            final AlbumImagemGateway albumImagemGateway,
            final AlbumGateway albumGateway
    ) {
        this.albumImagemGateway = Objects.requireNonNull(albumImagemGateway);
        this.albumGateway = Objects.requireNonNull(albumGateway);
    }

    @Override
    public List<AlbumImagemOutput> execute(final UUID aSecureId) {
        final var oAlbum = this.albumGateway.findBySecureId(aSecureId)
                .orElseThrow(() -> NotFoundException.with(Album.class, aSecureId));

        return this.albumImagemGateway.findByAlbumId(oAlbum.getId().getValue())
                .stream()
                .map(AlbumImagemOutput::from)
                .collect(Collectors.toList());
    }
}