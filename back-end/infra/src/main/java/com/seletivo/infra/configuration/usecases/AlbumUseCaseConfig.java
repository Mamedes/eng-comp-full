package com.seletivo.infra.configuration.usecases;

import com.seletivo.application.album.create.CreateAlbumUseCase;
import com.seletivo.application.album.create.DefaultCreateAlbumUseCase;
import com.seletivo.application.album.delete.DefaultDeleteAlbumUseCase;
import com.seletivo.application.album.delete.DeleteAlbumUseCase;
import com.seletivo.application.album.fetch.get.DefaultGetAlbumByIdUseCase;
import com.seletivo.application.album.fetch.get.GetAlbumByIdUseCase;
import com.seletivo.application.album.fetch.list.DefaultListAlbumUseCase;
import com.seletivo.application.album.fetch.list.ListAlbumUseCase;
import com.seletivo.application.album.update.DefaultUpdateAlbumUseCase;
import com.seletivo.application.album.update.UpdateAlbumUseCase;
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

    @Bean
    public GetAlbumByIdUseCase getAlbumByIdUseCase() {
        return new DefaultGetAlbumByIdUseCase(albumGateway, artistaGateway);
    }

    @Bean
    public ListAlbumUseCase listAlbumUseCase() {
        return new DefaultListAlbumUseCase(albumGateway, artistaGateway);
    }

    @Bean
    public UpdateAlbumUseCase updateAlbumUseCase(){
        return  new DefaultUpdateAlbumUseCase(albumGateway, artistaGateway);
    }

    @Bean
    public DeleteAlbumUseCase deleteAlbumUseCase(){
        return  new DefaultDeleteAlbumUseCase(albumGateway);
    }

}
