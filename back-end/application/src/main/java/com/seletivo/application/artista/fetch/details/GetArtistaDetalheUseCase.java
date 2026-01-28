package com.seletivo.application.artista.fetch.details;

import com.seletivo.application.UseCase;
import com.seletivo.application.album.albumImage.AlbumImagemOutput;
import com.seletivo.application.album.fetch.AlbumDetalheOutput;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.UUID;

public class GetArtistaDetalheUseCase extends UseCase<UUID, ArtistaDetalheOutput> {

    private final ArtistaGateway artistaGateway;
    private final AlbumGateway albumGateway;
    private final AlbumImagemGateway albumImagemGateway;

    public GetArtistaDetalheUseCase(
            final ArtistaGateway artistaGateway,
            final AlbumGateway albumGateway,
            final AlbumImagemGateway albumImagemGateway
    ) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
        this.albumGateway = Objects.requireNonNull(albumGateway);
        this.albumImagemGateway = Objects.requireNonNull(albumImagemGateway);
    }

    @Override
    public ArtistaDetalheOutput execute(final UUID anId) {
        final var artista = this.artistaGateway.findBySecureId(anId)
                .orElseThrow(() -> NotFoundException.with(Artista.class, anId));

        final var albuns = this.albumGateway.findAllByArtistaId(artista.getSecureId());

        final var albunsOutput = albuns.stream().map(album -> {
            final var imagens = this.albumImagemGateway.findByAlbumId(album.getId().getValue())
                    .stream()
                    .map(AlbumImagemOutput::from)
                    .toList();

            return AlbumDetalheOutput.from(album, imagens);
        }).toList();

        return ArtistaDetalheOutput.from(artista, albunsOutput);
    }
}