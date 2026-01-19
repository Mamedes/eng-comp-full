package com.seletivo.infra.configuration.usecases;

import com.seletivo.application.album.create.CreateAlbumUseCase;
import com.seletivo.application.album.create.DefaultCreateAlbumUseCase;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlbumUseCaseConfig {

    private final AlbumGateway albumGateway;
    private final ArtistaGateway artistaGateway;

    public AlbumUseCaseConfig(final AlbumGateway albumGateway, final ArtistaGateway artistaGateway) {
        this.albumGateway = albumGateway;
        this.artistaGateway = artistaGateway;
    }

    @Bean
    public CreateAlbumUseCase createAlbumUseCase() {
        return new DefaultCreateAlbumUseCase(albumGateway, artistaGateway);
    }
}
