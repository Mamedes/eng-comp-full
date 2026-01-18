package com.seletivo.application.artista.delete;

import com.seletivo.domain.artista.ArtistaGateway;
import java.util.Objects;
import java.util.UUID;

public class DefaultDeleteArtistaUseCase extends DeleteArtistaUseCase {

    private final ArtistaGateway artistaGateway;

    public DefaultDeleteArtistaUseCase(final ArtistaGateway artistaGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public void execute(final UUID aSecureId) {
        this.artistaGateway.deleteBySecureId(aSecureId);
    }
}
