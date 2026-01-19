package com.seletivo.infra.configuration.usecases;

import com.seletivo.application.album.albumImage.create.CreateAlbumImagemUseCase;
import com.seletivo.application.album.albumImage.create.DefaultCreateAlbumImagemUseCase;
import com.seletivo.application.album.albumImage.fetch.get.DefaultGetAlbumImagemByIdUseCase;
import com.seletivo.application.album.albumImage.fetch.get.GetAlbumImagemByIdUseCase;
import com.seletivo.application.album.albumImage.fetch.list.DefaultListAlbumImagensByAlbumUseCase;
import com.seletivo.application.album.albumImage.fetch.list.ListAlbumImagensByAlbumUseCase;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.arquivo.ArquivoStorageGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class AlbumImagemUseCaseConfig {

    private final AlbumImagemGateway albumImagemGateway;
    private final ArquivoStorageGateway arquivoStorageGateway;
    private final AlbumGateway albumGateway;

    public AlbumImagemUseCaseConfig(
            final AlbumImagemGateway albumImagemGateway,
            final ArquivoStorageGateway arquivoStorageGateway,
            final AlbumGateway albumGateway
    ) {
        this.albumImagemGateway = albumImagemGateway;
        this.arquivoStorageGateway = arquivoStorageGateway;
        this.albumGateway = Objects.requireNonNull(albumGateway);
    }

    @Bean
    public CreateAlbumImagemUseCase createAlbumImagemUseCase() {
        return new DefaultCreateAlbumImagemUseCase(albumImagemGateway, arquivoStorageGateway, albumGateway);
    }

    @Bean
    public GetAlbumImagemByIdUseCase getAlbumImagemByIdUseCase(){
        return  new DefaultGetAlbumImagemByIdUseCase(albumImagemGateway);
    }


    @Bean
    public ListAlbumImagensByAlbumUseCase listAlbumImagensByAlbumUseCase(){
        return new DefaultListAlbumImagensByAlbumUseCase(albumImagemGateway,albumGateway);
    }
}