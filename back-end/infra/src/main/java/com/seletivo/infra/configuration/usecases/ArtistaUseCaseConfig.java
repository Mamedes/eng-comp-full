package com.seletivo.infra.configuration.usecases;

import com.seletivo.application.artista.create.CreateArtistaUseCase;
import com.seletivo.application.artista.create.DefaultCreateArtistaUseCase;
import com.seletivo.application.artista.delete.DefaultDeleteArtistaUseCase;
import com.seletivo.application.artista.delete.DeleteArtistaUseCase;
import com.seletivo.application.artista.fetch.get.DefaultGetArtistaByIdUseCase;
import com.seletivo.application.artista.fetch.get.GetArtistaByIdUseCase;
import com.seletivo.application.artista.fetch.list.DefaultListArtistaUseCase;
import com.seletivo.application.artista.fetch.list.ListArtistaUseCase;
import com.seletivo.application.artista.update.DefaultUpdateArtistaUseCase;
import com.seletivo.application.artista.update.UpdateArtistaUseCase;
import com.seletivo.domain.artista.ArtistaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ArtistaUseCaseConfig {

    private final ArtistaGateway artistaGateway;

    public ArtistaUseCaseConfig(final ArtistaGateway artistaGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Bean
    public CreateArtistaUseCase createArtistaUseCase() {
        return new DefaultCreateArtistaUseCase(artistaGateway);
    }

    @Bean
    public GetArtistaByIdUseCase getArtistaByIdUseCase() {
        return new DefaultGetArtistaByIdUseCase(artistaGateway);
    }

    @Bean
    public ListArtistaUseCase listArtistaUseCase() {
        return new DefaultListArtistaUseCase(artistaGateway);
    }

    @Bean
    public UpdateArtistaUseCase updateArtistaUseCase() {
        return new DefaultUpdateArtistaUseCase(artistaGateway);
    }

    @Bean
    public DeleteArtistaUseCase deleteArtistaUseCase() {
        return new DefaultDeleteArtistaUseCase(artistaGateway);
    }
}
