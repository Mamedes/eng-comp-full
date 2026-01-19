package com.seletivo.application.album.fetch.get;


import com.seletivo.application.UseCase;
import com.seletivo.application.album.fetch.AlbumOutput;

import java.util.UUID;

public abstract class GetAlbumByIdUseCase
        extends UseCase<UUID, AlbumOutput> {
}