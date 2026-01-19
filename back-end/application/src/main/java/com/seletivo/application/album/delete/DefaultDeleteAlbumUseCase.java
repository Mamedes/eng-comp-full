package com.seletivo.application.album.delete;


import com.seletivo.domain.album.AlbumGateway;
import java.util.Objects;
import java.util.UUID;

public class DefaultDeleteAlbumUseCase extends DeleteAlbumUseCase {

    private final AlbumGateway albumGateway;

    public DefaultDeleteAlbumUseCase(final AlbumGateway albumGateway) {
        this.albumGateway = Objects.requireNonNull(albumGateway);
    }

    @Override
    public void execute(final UUID aSecureId) {
        this.albumGateway.deleteBySecureId(aSecureId);
    }
}