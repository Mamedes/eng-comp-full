package com.seletivo.application.album;

import com.seletivo.domain.album.Album;
import com.seletivo.domain.artista.ArtistaID;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void givenValidParams_whenCallNewAlbum_thenShouldInstantiateAnAlbum() {
        final var expectedTitulo = "Os maninhos";
        final var expectedArtistas = Set.of(ArtistaID.from(1L, "uuid-1"));

        final var actualAlbum = Album.newAlbum(expectedTitulo, expectedArtistas);

        assertNotNull(actualAlbum);
        assertNotNull(actualAlbum.getSecureId());
        assertEquals(expectedTitulo, actualAlbum.getTitulo());
        assertEquals(expectedArtistas, actualAlbum.getArtistas());
        assertNotNull(actualAlbum.getCreatedAt());
        assertNotNull(actualAlbum.getUpdatedAt());
    }

    @Test
    void givenAValidAlbum_whenCallUpdate_thenShouldReturnUpdatedAlbum() {
        final var expectedTitulo = "Pisadinha (Live)";
        final var expectedArtistas = Set.of(ArtistaID.from(1L, "uuid-1"));
        final var aAlbum = Album.newAlbum("Pisadinha", expectedArtistas);

        final var previousUpdatedAt = aAlbum.getUpdatedAt();

        final var actualAlbum = aAlbum.update(expectedTitulo, expectedArtistas);

        assertEquals(expectedTitulo, actualAlbum.getTitulo());
        assertEquals(expectedArtistas, actualAlbum.getArtistas());
        assertEquals(aAlbum.getCreatedAt(), actualAlbum.getCreatedAt());
        assertTrue(actualAlbum.getUpdatedAt().isAfter(previousUpdatedAt));
    }
}