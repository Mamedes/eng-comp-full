package com.seletivo.application.artista.update;


import com.seletivo.application.UseCase;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateArtistaUseCase
        extends UseCase<UpdateArtistaCommand, Either<Notification, UpdateArtistaOutput>> {
}