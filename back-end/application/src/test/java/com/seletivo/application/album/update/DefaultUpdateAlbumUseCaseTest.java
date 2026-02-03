package com.seletivo.application.album.update;


import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUpdateAlbumUseCaseTest {

    @InjectMocks
    private DefaultUpdateAlbumUseCase useCase;

    @Mock
    private AlbumGateway albumGateway;

    @Mock
    private ArtistaGateway artistaGateway;

    @Test
    void givenAValidCommand_whenCallsUpdateAlbum_thenShouldReturnOutput() {
        final var aArtista = ArtistaID.from(1L, UUID.randomUUID().toString());
        final var anAlbum = Album.newAlbum("Old Title", Set.of(aArtista));

        final var expectedId = anAlbum.getSecureId();
        final var expectedTitulo = "New Title";
        final var expectedArtistasIds = Set.of(UUID.fromString(aArtista.getSecureId()));

        final var aCommand = UpdateAlbumCommand.with(expectedId, expectedTitulo, expectedArtistasIds);

        when(albumGateway.findBySecureId(expectedId)).thenReturn(Optional.of(anAlbum));
        when(artistaGateway.existsBySecureIds(any())).thenReturn(List.of(aArtista));
        when(albumGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.secureId());

        verify(albumGateway, times(1)).update(argThat(updatedAlbum ->
                updatedAlbum.getTitulo().equals(expectedTitulo) &&
                        updatedAlbum.getSecureId().equals(expectedId)
        ));
    }

    @Test
    void givenACommandWithInvalidAlbumId_whenCallsUpdateAlbum_thenShouldThrowNotFoundException() {
        final var expectedId = UUID.randomUUID();
        final var aCommand = UpdateAlbumCommand.with(expectedId, "Title", Set.of());

        when(albumGateway.findBySecureId(expectedId)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));
        assertTrue(exception.getMessage().contains(expectedId.toString()));

        verify(albumGateway, never()).update(any());
    }

    @Test
    void givenAnInvalidNullTitle_whenCallsUpdateAlbum_thenShouldReturnNotification() {
        final var aArtista = ArtistaID.from(1L, UUID.randomUUID().toString());
        final var anAlbum = Album.newAlbum("Valid Title", Set.of(aArtista));

        final var expectedId = anAlbum.getSecureId();
        final String expectedTitulo = null;
        final var expectedErrorMessage = "'albumTitulo' should not be empty";

        final var aCommand = UpdateAlbumCommand.with(expectedId, expectedTitulo, Set.of(UUID.fromString(aArtista.getSecureId())));

        when(albumGateway.findBySecureId(expectedId)).thenReturn(Optional.of(anAlbum));
        when(artistaGateway.existsBySecureIds(any())).thenReturn(List.of(aArtista));

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(1, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(albumGateway, never()).update(any());
    }

    @Test
    void givenACommandWithNonExistentArtista_whenCallsUpdateAlbum_thenShouldReturnNotification() {
        final var anAlbum = Album.newAlbum("Title", Set.of());
        final var expectedId = anAlbum.getSecureId();
        final var nonExistentArtistaId = UUID.randomUUID();

        final var aCommand = UpdateAlbumCommand.with(expectedId, "New Title", Set.of(nonExistentArtistaId));

        when(albumGateway.findBySecureId(expectedId)).thenReturn(Optional.of(anAlbum));
        when(artistaGateway.existsBySecureIds(any())).thenReturn(List.of()); // Retorna vazio = não achou

        // Execução
        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals("Um ou mais artistas não foram encontrados.", notification.firstError().message());
        verify(albumGateway, never()).update(any());
    }
}