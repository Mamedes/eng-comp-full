package com.seletivo.infra.api.controller.albumImage;

import com.seletivo.application.album.albumImage.create.CreateAlbumImagemCommand;
import com.seletivo.application.album.albumImage.create.CreateAlbumImagemOutput;
import com.seletivo.application.album.albumImage.create.CreateAlbumImagemUseCase;
import com.seletivo.application.arquivo.ArquivoDTO;
import com.seletivo.domain.validation.handler.Notification;
import com.seletivo.infra.api.controller.albumImage.presenter.AlbumImagemApiPresenter;
import com.seletivo.infra.api.controller.albumImage.request.CreateAlbumImagemRequest;
import com.seletivo.infra.api.controller.albumImage.request.UpdateAlbumImagemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class AlbumImagemController implements AlbumImagemAPI {
    private final CreateAlbumImagemUseCase createAlbumImagemUseCase;
  //  private final ListAlbumImagemUseCase listAlbumImagemUseCase;
 //   private final GetAlbumImagemByIdUseCase getAlbumImagemByIdUseCase;
   private final AlbumImagemApiPresenter albumImagemApiPresenter;

    public AlbumImagemController(
            final CreateAlbumImagemUseCase createAlbumImagemUseCase,
    //        final ListAlbumImagemUseCase listAlbumImagemUseCase,
    //        final GetAlbumImagemByIdUseCase getAlbumImagemByIdUseCase,
            final AlbumImagemApiPresenter albumImagemApiPresenter
    ) {
        this.createAlbumImagemUseCase = Objects.requireNonNull(createAlbumImagemUseCase);
   //     this.listAlbumImagemUseCase = Objects.requireNonNull(listAlbumImagemUseCase);
   //     this.getAlbumImagemByIdUseCase = Objects.requireNonNull(getAlbumImagemByIdUseCase);
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
            List<Long> ids = outputs.stream().map(CreateAlbumImagemOutput::id).collect(Collectors.toList());
            return ResponseEntity.created(URI.create("/album-imagem/" + ids.get(0))).body(outputs);
        };

        return this.createAlbumImagemUseCase.execute(aCommand).fold(onError, onSuccess);
    }
    @Override
    public void listImagens(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {

    }

    @Override
    public void getById(final Long id) {
    }

    @Override
    public ResponseEntity<?> updateAlbumImagem(final Long id, final UpdateAlbumImagemRequest input) {
        return null;
    }

    @Override
    public void deleteById(final Long id) {
    }
}
