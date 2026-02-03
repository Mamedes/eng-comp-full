package com.seletivo.application.artista.fetch.get;

import com.seletivo.application.artista.fetch.ArtistaOutput;
import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.exceptions.NotFoundException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class DefaultGetArtistaByIdUseCase extends GetArtistaByIdUseCase {

    private final ArtistaGateway artistaGateway;

    public DefaultGetArtistaByIdUseCase(final ArtistaGateway artistaGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public ArtistaOutput execute(final UUID aSecureId) {
        return this.artistaGateway.findBySecureId(aSecureId)
                .map(ArtistaOutput::from)
                .orElseThrow(notFound(aSecureId));
    }

    private Supplier<NotFoundException> notFound(final UUID aSecureId) {
        return () -> NotFoundException.with(Artista.class, aSecureId);
    }
}