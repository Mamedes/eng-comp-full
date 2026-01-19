package com.seletivo.application.album.albumImage.create;


import com.seletivo.application.arquivo.ArquivoDTO;
import java.util.List;

public record CreateAlbumImagemCommand(Long albumId, List<ArquivoDTO> arquivos) {

    public static CreateAlbumImagemCommand with(final Long albumId, final List<ArquivoDTO> arquivos) {
        return new CreateAlbumImagemCommand(albumId, arquivos);
    }
}
