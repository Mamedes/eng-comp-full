package com.seletivo.infra.artista;

import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.infra.persistence.artista.ArtistaPostgresSQLGateway;
import com.seletivo.infra.persistence.artista.ArtistaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ArtistaPostgresSQLGateway.class)
class ArtistaPostgreSQLGatewayIT {

    @Autowired
    private ArtistaGateway artistaGateway;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Test
    void givenAValidArtista_whenCallsCreate_thenShouldPersistArtista() {
        final var expectedNome = "Alceu Valença";
        final var expectedTipo = "Cantor";
        final var aArtista = Artista.newArtista(expectedNome, expectedTipo);

        final var actualArtista = artistaGateway.create(aArtista);

        Assertions.assertNotNull(actualArtista.getId());
        Assertions.assertEquals(expectedNome, actualArtista.getNome());
        Assertions.assertEquals(expectedTipo, actualArtista.getTipo());
        Assertions.assertEquals(aArtista.getSecureId(), actualArtista.getSecureId());

        final var persistedArtista = artistaRepository.findById(actualArtista.getId().getValue()).get();
        Assertions.assertEquals(expectedNome, persistedArtista.getNome());
    }

    @Test
    void givenAValidId_whenCallsFindBySecureId_thenShouldReturnArtista() {
        final var aArtista = Artista.newArtista("Gilsons", "Banda");
        artistaGateway.create(aArtista);
        final var expectedSecureId = aArtista.getSecureId();

        final var actualArtista = artistaGateway.findBySecureId(expectedSecureId).get();

        Assertions.assertEquals(aArtista.getSecureId(), actualArtista.getSecureId());
        Assertions.assertEquals("Gilsons", actualArtista.getNome());
    }

    @Test
    void givenAnInvalidId_whenCallsDeleteBySecureId_thenShouldBeOk() {
        Assertions.assertDoesNotThrow(() -> artistaGateway.deleteBySecureId(java.util.UUID.randomUUID()));
    }
}