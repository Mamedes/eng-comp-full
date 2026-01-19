package com.seletivo.application.album.albumImage.fetch.get;

import com.seletivo.application.album.albumImage.AlbumImagemOutput;
import com.seletivo.domain.album.AlbumImagem;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class DefaultGetAlbumImagemByIdUseCase extends GetAlbumImagemByIdUseCase {

    private final AlbumImagemGateway albumImagemGateway;

    public DefaultGetAlbumImagemByIdUseCase(final AlbumImagemGateway albumImagemGateway) {
        this.albumImagemGateway = Objects.requireNonNull(albumImagemGateway);
    }

    @Override
    public AlbumImagemOutput execute(final UUID anId) {

        return this.albumImagemGateway.findBySecureId(anId)
                .map(AlbumImagemOutput::from)
                .orElseThrow(notFound(anId));
    }

    private Supplier<NotFoundException> notFound(final UUID anId) {
        return () -> NotFoundException.with(AlbumImagem.class, anId);
    }
}
