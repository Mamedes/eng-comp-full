package com.seletivo.application.album.albumImage.delete;

import com.seletivo.domain.album.AlbumImagem;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.arquivo.ArquivoStorageGateway;
import com.seletivo.domain.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

public class DefaultDeleteAlbumImagemUseCase extends  DeleteAlbumImagemUseCase{

    private final AlbumImagemGateway albumImagemGateway;
    private final ArquivoStorageGateway arquivoStorageGateway;

    public DefaultDeleteAlbumImagemUseCase(
            final AlbumImagemGateway albumImagemGateway,
            final ArquivoStorageGateway arquivoStorageGateway
    ) {
        this.albumImagemGateway = Objects.requireNonNull(albumImagemGateway);
        this.arquivoStorageGateway = Objects.requireNonNull(arquivoStorageGateway);
    }

    @Transactional
    public void execute(final UUID aSecureId) {
        final var anImage = this.albumImagemGateway.findBySecureId(aSecureId)
                .orElseThrow(() -> NotFoundException.with(AlbumImagem.class, aSecureId));

        try {
            this.albumImagemGateway.deleteById(anImage.getId());

            this.arquivoStorageGateway.deleteArquivo(anImage.getFileKey());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar imagem: " + e.getMessage(), e);
        }
    }
}