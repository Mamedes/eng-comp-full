package com.seletivo.application.artista.fetch.get;


import com.seletivo.application.UseCase;
import com.seletivo.application.artista.fetch.ArtistaOutput;

import java.util.UUID;

public abstract class GetArtistaByIdUseCase
        extends UseCase<UUID, ArtistaOutput> {
}
