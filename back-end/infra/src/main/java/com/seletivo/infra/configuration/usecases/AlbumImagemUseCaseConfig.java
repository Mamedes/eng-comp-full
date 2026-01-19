package com.seletivo.infra.configuration.usecases;



import com.seletivo.application.album.albumImage.create.CreateAlbumImagemUseCase;
import com.seletivo.application.album.albumImage.create.DefaultCreateAlbumImagemUseCase;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.arquivo.ArquivoStorageGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlbumImagemUseCaseConfig {

    private final AlbumImagemGateway albumImagemGateway;
    private final ArquivoStorageGateway arquivoStorageGateway;

    public AlbumImagemUseCaseConfig(
            final AlbumImagemGateway albumImagemGateway,
            final ArquivoStorageGateway arquivoStorageGateway
    ) {
        this.albumImagemGateway = albumImagemGateway;
        this.arquivoStorageGateway = arquivoStorageGateway;
    }

    @Bean
    public CreateAlbumImagemUseCase createAlbumImagemUseCase() {
        return new DefaultCreateAlbumImagemUseCase(albumImagemGateway, arquivoStorageGateway);
    }
}