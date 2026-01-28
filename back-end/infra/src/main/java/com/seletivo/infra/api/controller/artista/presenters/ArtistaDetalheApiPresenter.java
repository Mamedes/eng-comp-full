package com.seletivo.infra.api.controller.artista.presenters;


import com.seletivo.application.artista.fetch.details.ArtistaDetalheOutput;
import com.seletivo.infra.api.controller.albumImage.presenter.AlbumImagemApiPresenter;
import com.seletivo.infra.api.controller.artista.response.AlbumDetalheResponse;
import com.seletivo.infra.api.controller.artista.response.ArtistaDetalheResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ArtistaDetalheApiPresenter {

    private final AlbumImagemApiPresenter albumImagemApiPresenter;

    public ArtistaDetalheApiPresenter(final AlbumImagemApiPresenter albumImagemApiPresenter) {
        this.albumImagemApiPresenter = Objects.requireNonNull(albumImagemApiPresenter);
    }

    public ArtistaDetalheResponse present(final ArtistaDetalheOutput output) {
        final var albunsResponse = output.albuns().stream()
                .map(album -> new AlbumDetalheResponse(
                        album.id(),
                        album.titulo(),
                        album.imagens().stream()
                                .map(albumImagemApiPresenter::present)
                                .toList()
                ))
                .toList();

        return new ArtistaDetalheResponse(
                output.id(),
                output.nome(),
                output.tipo(),
                output.createdAt(),
                output.updatedAt(),
                albunsResponse
        );
    }
}