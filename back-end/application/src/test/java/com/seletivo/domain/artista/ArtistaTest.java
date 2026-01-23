package com.seletivo.domain.artista;

import com.seletivo.domain.validation.handler.Notification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistaTest {

    @Test
    void shouldCreateArtistaWithGeneratedSecureId() {
        final var expectedNome = "Pink Floyd";
        final var expectedTipo = "Banda";

        final var actualArtista = Artista.newArtista(expectedNome, expectedTipo);

        assertNotNull(actualArtista);
        assertNull(actualArtista.getId());
        assertNotNull(actualArtista.getSecureId());
        assertEquals(expectedNome, actualArtista.getNome());
        assertEquals(expectedTipo, actualArtista.getTipo());
        assertNotNull(actualArtista.getCreatedAt());
        assertNotNull(actualArtista.getUpdatedAt());
    }

    @Test
    void givenInvalidNullNome_whenCallNewArtistaAndValidate_thenReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nome' should not be empty";
        final var actualArtista = Artista.newArtista(null, "Banda");

        final var notification = Notification.create();
        actualArtista.validate(notification);

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    void givenInvalidEmptyNome_whenCallNewArtistaAndValidate_thenReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nome' should not be empty";
        final var actualArtista = Artista.newArtista("   ", "Banda");

        final var notification = Notification.create();
        actualArtista.validate(notification);

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    void givenInvalidNullTipo_whenCallNewArtistaAndValidate_thenReceiveError() {
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'tipo' should not be empty";
        final var actualArtista = Artista.newArtista("Pink Floyd", null);

        final var notification = Notification.create();
        actualArtista.validate(notification);

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
    }
}