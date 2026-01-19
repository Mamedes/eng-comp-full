package com.seletivo.application.album.albumImage.create;

import com.seletivo.application.arquivo.ArquivoDTO;
import com.seletivo.application.utils.FileUtils;
import com.seletivo.domain.album.AlbumImagem;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.arquivo.ArquivoStorageGateway;
import com.seletivo.domain.validation.Error;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;

public class DefaultCreateAlbumImagemUseCase extends CreateAlbumImagemUseCase {

    private final AlbumImagemGateway albumImagemGateway;
    private final ArquivoStorageGateway arquivoStorageGateway;
    // private final AlbumGateway albumGateway; // Adicionar se houver validação de existência do álbum

    public DefaultCreateAlbumImagemUseCase(
            final AlbumImagemGateway albumImagemGateway,
            final ArquivoStorageGateway arquivoStorageGateway
    ) {
        this.albumImagemGateway = Objects.requireNonNull(albumImagemGateway);
        this.arquivoStorageGateway = Objects.requireNonNull(arquivoStorageGateway);
    }

    @Override
    @Transactional
    public Either<Notification, List<CreateAlbumImagemOutput>> execute(final CreateAlbumImagemCommand aCommand) {
        final var albumId = aCommand.albumId();
        final var arquivos = aCommand.arquivos();
        final var notification = Notification.create();

        if (arquivos == null || arquivos.isEmpty()) {
            return Left(notification.append(new Error("Files are required")));
        }

        final List<CreateAlbumImagemOutput> outputs = new ArrayList<>();
        final List<String> arquivosEnviados = new ArrayList<>();

        try {
            for (ArquivoDTO arquivoDTO : arquivos) {
                final String randomHash = FileUtils.generateRandomHash();
                final String bucketPath = "album-" + albumId + "/" + randomHash + "." + FileUtils.getFileExtension(arquivoDTO.nomeArquivo());

                // Cria o objeto de domínio seguindo o padrão da migration SQL
                final var anAlbumImagem = AlbumImagem.newAlbumImagem(
                        albumId,
                        arquivoDTO.nomeArquivo(),
                        bucketPath,
                        arquivoDTO.tipoConteudo()
                );

                anAlbumImagem.validate(notification);

                if (notification.hasError()) {
                    return Left(notification);
                }

                final var savedImagem = this.albumImagemGateway.create(anAlbumImagem);

                arquivoStorageGateway.uploadArquivo(
                        arquivoDTO.conteudo(),
                        arquivoDTO.nomeArquivo(),
                        arquivoDTO.tipoConteudo(),
                        bucketPath
                );

                arquivosEnviados.add(bucketPath);
                outputs.add(CreateAlbumImagemOutput.from(savedImagem));
            }
        } catch (Exception e) {
            // Rollback manual do storage em caso de falha no loop
            try {
                arquivoStorageGateway.deleteArquivos(arquivosEnviados);
            } catch (Exception ex) {
                throw new RuntimeException("Erro no rollback do storage: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Erro ao salvar imagens do álbum: " + e.getMessage(), e);
        }

        return Right(outputs);
    }
}
