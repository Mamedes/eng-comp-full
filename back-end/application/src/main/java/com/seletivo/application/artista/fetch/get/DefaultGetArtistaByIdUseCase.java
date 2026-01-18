package com.seletivo.application.artista.fetch.get;

import com.seletivo.application.artista.fetch.ArtistaOutput;
import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.exceptions.NotFoundException;
import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetArtistaByIdUseCase extends GetArtistaByIdUseCase {

    private final ArtistaGateway artistaGateway;

    public DefaultGetArtistaByIdUseCase(final ArtistaGateway artistaGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public ArtistaOutput execute(final Long anIn) {
        final var anArtistaId = ArtistaID.from(anIn);

        return this.artistaGateway.findById(anArtistaId)
                .map(ArtistaOutput::from)
                .orElseThrow(notFound(anArtistaId));
    }

    private Supplier<NotFoundException> notFound(final ArtistaID anId) {
        return () -> NotFoundException.with(Artista.class, anId);
    }
}