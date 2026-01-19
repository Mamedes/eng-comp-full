package com.seletivo.infra.api.controller.album.presenter;


import com.seletivo.application.album.fetch.AlbumListOutput;
import com.seletivo.application.album.fetch.AlbumOutput;
import com.seletivo.infra.api.controller.album.AlbumListResponse;
import com.seletivo.infra.api.controller.album.AlbumResponse;

public interface AlbumApiPresenter {

    static AlbumResponse present(final AlbumOutput output) {
        return new AlbumResponse(
                output.secureId(),
                output.titulo(),
                output.artistaId()
        );
    }

    static AlbumListResponse present(final AlbumListOutput output) {
        return new AlbumListResponse(
                output.secureId(),
                output.titulo(),
                output.artistaId()
        );
    }
}
