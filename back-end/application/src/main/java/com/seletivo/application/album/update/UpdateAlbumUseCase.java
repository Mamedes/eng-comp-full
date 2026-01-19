package com.seletivo.application.album.update;

import com.seletivo.application.UseCase;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateAlbumUseCase
        extends UseCase<UpdateAlbumCommand, Either<Notification, UpdateAlbumOutput>> {
}
