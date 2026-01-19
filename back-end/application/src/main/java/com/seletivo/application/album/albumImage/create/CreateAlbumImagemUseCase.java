package com.seletivo.application.album.albumImage.create;

import com.seletivo.application.UseCase;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.List;

public abstract class CreateAlbumImagemUseCase
        extends UseCase<CreateAlbumImagemCommand, Either<Notification, List<CreateAlbumImagemOutput>>> {
}
