package com.seletivo.infra.api.controller.albumImage;

import com.seletivo.application.album.albumImage.create.CreateAlbumImagemCommand;
import com.seletivo.application.album.albumImage.create.CreateAlbumImagemOutput;
import com.seletivo.application.album.albumImage.create.CreateAlbumImagemUseCase;
import com.seletivo.application.album.albumImage.fetch.get.GetAlbumImagemByIdUseCase;
import com.seletivo.application.album.albumImage.fetch.list.ListAlbumImagensByAlbumUseCase;
import com.seletivo.application.arquivo.ArquivoDTO;
import com.seletivo.domain.validation.handler.Notification;
import com.seletivo.infra.api.controller.albumImage.presenter.AlbumImagemApiPresenter;
import com.seletivo.infra.api.controller.albumImage.request.CreateAlbumImagemRequest;
import com.seletivo.infra.api.controller.albumImage.request.UpdateAlbumImagemRequest;
import com.seletivo.infra.api.controller.albumImage.response.AlbumImagemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class AlbumImagemController implements AlbumImagemAPI {
    private final CreateAlbumImagemUseCase createAlbumImagemUseCase;
   private final ListAlbumImagensByAlbumUseCase  listAlbumImagensByAlbumUseCase;
    private final GetAlbumImagemByIdUseCase getAlbumImagemByIdUseCase;
   private final AlbumImagemApiPresenter albumImagemApiPresenter;

    public AlbumImagemController(
            final CreateAlbumImagemUseCase createAlbumImagemUseCase,
            final ListAlbumImagensByAlbumUseCase listAlbumImagensByAlbumUseCase,
            final GetAlbumImagemByIdUseCase getAlbumImagemByIdUseCase,
            final AlbumImagemApiPresenter albumImagemApiPresenter
    ) {
        this.createAlbumImagemUseCase = Objects.requireNonNull(createAlbumImagemUseCase);
        this.listAlbumImagensByAlbumUseCase = Objects.requireNonNull(listAlbumImagensByAlbumUseCase);
        this.getAlbumImagemByIdUseCase = Objects.requireNonNull(getAlbumImagemByIdUseCase);
        this.albumImagemApiPresenter = Objects.requireNonNull(albumImagemApiPresenter);
    }

    @Override
    public ResponseEntity<?> createImagem(final CreateAlbumImagemRequest input) {
        final List<ArquivoDTO> arquivosDTO = new ArrayList<>();

        for (MultipartFile file : input.files()) {
            try {
                arquivosDTO.add(new ArquivoDTO(file.getBytes(), file.getOriginalFilename(), file.getContentType()));
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Erro ao ler o arquivo: " + e.getMessage());
            }
        }

        final var aCommand = CreateAlbumImagemCommand.with(input.albumId(), arquivosDTO);

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<List<CreateAlbumImagemOutput>, ResponseEntity<?>> onSuccess = outputs -> {
            List<UUID> ids = outputs.stream().map(CreateAlbumImagemOutput::id).collect(Collectors.toList());
            return ResponseEntity.created(URI.create("/album-imagem/" + ids.get(0))).body(outputs);
        };

        return this.createAlbumImagemUseCase.execute(aCommand).fold(onError, onSuccess);
    }
    @Override
    public List<AlbumImagemResponse> listByAlbum(final UUID secureId) {
        return this.listAlbumImagensByAlbumUseCase.execute(secureId)
                .stream()
                .map(this.albumImagemApiPresenter::present)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumImagemResponse getById(final UUID id) {
        return this.albumImagemApiPresenter.present(this.getAlbumImagemByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateAlbumImagem(final Long id, final UpdateAlbumImagemRequest input) {
        return null;
    }

    @Override
    public void deleteById(final Long id) {
    }
}
