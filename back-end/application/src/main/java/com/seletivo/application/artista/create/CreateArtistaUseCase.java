package com.seletivo.application.artista.create;

import com.seletivo.application.UseCase;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateArtistaUseCase
        extends UseCase<CreateArtistaCommand, Either<Notification, CreateArtistaOutput>> {
}