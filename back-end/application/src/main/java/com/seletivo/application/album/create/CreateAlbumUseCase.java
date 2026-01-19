package com.seletivo.application.album.create;

import com.seletivo.application.UseCase;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateAlbumUseCase
        extends UseCase<CreateAlbumCommand, Either<Notification, CreateAlbumOutput>> {
}
