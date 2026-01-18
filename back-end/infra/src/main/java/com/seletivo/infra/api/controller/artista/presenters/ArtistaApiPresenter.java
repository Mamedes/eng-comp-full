package com.seletivo.infra.api.controller.artista.presenters;

import com.seletivo.application.artista.fetch.ArtistaOutput;
import com.seletivo.application.artista.fetch.list.ArtistaListOutput;
import com.seletivo.infra.api.controller.artista.ArtistaListResponse;
import com.seletivo.infra.api.controller.artista.ArtistaResponse;

public interface ArtistaApiPresenter {

    static ArtistaResponse present(final ArtistaOutput output) {
        return new ArtistaResponse(
                output.id(),
                output.secureId(),
                output.nome(),
                output.tipo()
        );
    }

    static ArtistaListResponse present(final ArtistaListOutput output) {
        return new ArtistaListResponse(
                output.id(),
                output.secureId(),
                output.nome(),
                output.tipo()
        );
    }
}