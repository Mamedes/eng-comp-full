package com.seletivo.infra.album;

import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.pagination.SearchQuery;
import com.seletivo.infra.persistence.album.AlbumPostgresSQLGateway;
import com.seletivo.infra.persistence.album.AlbumRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.UUID;

@ActiveProfiles("dev")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AlbumPostgresSQLGateway.class)
class AlbumPostgreSQLGatewayIT {

    @Autowired
    private AlbumGateway albumGateway;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    void givenAValidAlbum_whenCallsCreate_thenShouldPersistAlbum() {
        final var expectedTitulo = "Amado Batista";
        final var expectedArtistas = Set.of(ArtistaID.from(1L, UUID.randomUUID().toString()));
        final var anAlbum = Album.newAlbum(expectedTitulo, expectedArtistas);

        final var actualAlbum = albumGateway.create(anAlbum);

        Assertions.assertNotNull(actualAlbum.getId());
        Assertions.assertEquals(expectedTitulo, actualAlbum.getTitulo());
        Assertions.assertEquals(expectedArtistas.size(), actualAlbum.getArtistas().size());
        Assertions.assertEquals(anAlbum.getSecureId(), actualAlbum.getSecureId());

        final var persistedAlbum = albumRepository.findById(actualAlbum.getId().getValue()).get();
        Assertions.assertEquals(expectedTitulo, persistedAlbum.getTitulo());
        Assertions.assertEquals(1, persistedAlbum.getArtistaIds().size());
    }

    @Test
    void givenAValidSecureId_whenCallsFindBySecureId_thenShouldReturnAlbum() {
        final var anAlbum = Album.newAlbum("Tempo de Festival", Set.of());
        albumGateway.create(anAlbum);

        final var expectedSecureId = anAlbum.getSecureId();

        final var actualAlbum = albumGateway.findBySecureId(expectedSecureId).get();

        Assertions.assertEquals(expectedSecureId, actualAlbum.getSecureId());
        Assertions.assertEquals("Tempo de Festival", actualAlbum.getTitulo());
    }

    @Test
    void givenAValidAlbum_whenCallsUpdate_thenShouldRefreshData() {
        final var anAlbum = Album.newAlbum("Titulo Antigo", Set.of());
        albumGateway.create(anAlbum);

        final var expectedTitulo = "Titulo Novo";
        final var updatedAlbum = anAlbum.update(expectedTitulo, anAlbum.getArtistas());

        final var actualAlbum = albumGateway.update(updatedAlbum);

        Assertions.assertEquals(expectedTitulo, actualAlbum.getTitulo());
        Assertions.assertNotEquals(anAlbum.getUpdatedAt(), actualAlbum.getUpdatedAt());
    }

    @Test
    void givenAValidQuery_whenCallsFindAll_thenShouldReturnPaginated() {
        albumGateway.create(Album.newAlbum("Album A", Set.of()));
        albumGateway.create(Album.newAlbum("Album B", Set.of()));

        final var query = new SearchQuery(0, 1, "", "titulo", "asc");
        final var actualResult = albumGateway.findAll(query);

        Assertions.assertEquals(0, actualResult.currentPage());
        Assertions.assertEquals(1, actualResult.perPage());
        Assertions.assertEquals(2, actualResult.total());
        Assertions.assertEquals(1, actualResult.items().size());
        Assertions.assertEquals("Album A", actualResult.items().get(0).getTitulo());
    }

    @Test
    void givenAValidSecureId_whenCallsDelete_thenShouldDeleteAlbum() {
        final var anAlbum = albumGateway.create(Album.newAlbum("Para Deletar", Set.of()));
        final var secureId = anAlbum.getSecureId();

        Assertions.assertTrue(albumRepository.findBySecureId(secureId).isPresent());

        albumGateway.deleteBySecureId(secureId);

        Assertions.assertTrue(albumRepository.findBySecureId(secureId).isEmpty());
    }
}