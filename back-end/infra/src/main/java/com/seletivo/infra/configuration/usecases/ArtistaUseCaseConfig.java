package com.seletivo.infra.configuration.usecases;

import com.seletivo.application.album.fetch.projection.ListArtistaViewUseCase;
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
import com.seletivo.domain.artista.ArtistaQueryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ArtistaUseCaseConfig {

    private final ArtistaGateway artistaGateway;
    private final ArtistaQueryGateway artistaQueryGateway;

    public ArtistaUseCaseConfig(final ArtistaGateway artistaGateway,final ArtistaQueryGateway artistaQueryGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
        this.artistaQueryGateway = Objects.requireNonNull(artistaQueryGateway);
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

    @Bean
    public ListArtistaViewUseCase listArtistaProjectionDashboardUseCase() {
        return new ListArtistaViewUseCase(artistaQueryGateway);
    }
}
