package com.seletivo.application.regional.sync;

import com.seletivo.application.UseCase;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class SyncRegionaisUseCase
        extends UseCase<SyncRegionaisCommand, Either<Notification, SyncRegionaisOutput>> {
}