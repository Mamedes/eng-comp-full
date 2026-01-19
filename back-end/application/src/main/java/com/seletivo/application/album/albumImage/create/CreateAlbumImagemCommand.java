package com.seletivo.application.album.albumImage.create;


import com.seletivo.application.arquivo.ArquivoDTO;
import java.util.List;
import java.util.UUID;

public record CreateAlbumImagemCommand(UUID albumId, List<ArquivoDTO> arquivos) {

    public static CreateAlbumImagemCommand with(final UUID albumId, final List<ArquivoDTO> arquivos) {
        return new CreateAlbumImagemCommand(albumId, arquivos);
    }
}
