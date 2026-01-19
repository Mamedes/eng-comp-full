package com.seletivo.application.album.albumImage.fetch.list;

import com.seletivo.application.UseCase;
import com.seletivo.application.album.albumImage.AlbumImagemOutput;

import java.util.List;
import java.util.UUID;

public abstract class ListAlbumImagensByAlbumUseCase
        extends UseCase<UUID, List<AlbumImagemOutput>> {
}