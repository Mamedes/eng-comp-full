package com.seletivo.application.album.create;

import com.seletivo.application.album.notification.AlbumNotificationService;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.artista.ArtistaID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultCreateAlbumUseCaseTest {

    @InjectMocks
    private DefaultCreateAlbumUseCase useCase;

    @Mock
    private AlbumGateway albumGateway;

    @Mock
    private ArtistaGateway artistaGateway;

    @Mock
    private AlbumNotificationService notificationService;

    @Test
    void givenAValidCommand_whenCallsCreateAlbum_thenShouldReturnAlbumId() {
        final var expectedTitulo = "Meteora";
        final var expectedArtistaId = UUID.randomUUID();
        final var expectedArtistasIds = Set.of(expectedArtistaId);
        final var expectedArtistaIDObj = ArtistaID.from(1L, expectedArtistaId.toString());

        final var aCommand = CreateAlbumCommand.with(expectedTitulo, expectedArtistasIds);

        when(artistaGateway.existsBySecureIds(any()))
                .thenReturn(List.of(expectedArtistaIDObj));

        when(albumGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(artistaGateway, times(1)).existsBySecureIds(expectedArtistasIds);
        verify(albumGateway, times(1)).create(argThat(album ->
                Objects.equals(expectedTitulo, album.getTitulo())
                        && album.getArtistas().contains(expectedArtistaIDObj)
                        && Objects.nonNull(album.getSecureId())
        ));
        verify(notificationService, times(1)).notifyAlbumCreated(anyString());
    }

    @Test
    void givenACommandWithNonExistentArtista_whenCallsCreateAlbum_thenShouldReturnNotification() {
        final var expectedTitulo = "Meteora";
        final var artistaId1 = UUID.randomUUID();
        final var artistaId2 = UUID.randomUUID();
        final var commandIds = Set.of(artistaId1, artistaId2);

        final var expectedErrorMessage = "Um ou mais artistas não foram encontrados";
        final var expectedErrorCount = 1;

        final var aCommand = CreateAlbumCommand.with(expectedTitulo, commandIds);

        when(artistaGateway.existsBySecureIds(any()))
                .thenReturn(List.of(ArtistaID.from(1L, artistaId1.toString())));

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(albumGateway, never()).create(any());
        verify(notificationService, never()).notifyAlbumCreated(anyString());
    }

    @Test
    void givenAnInvalidEmptyTitulo_whenCallsCreateAlbum_thenShouldReturnNotification() {
        final var expectedTitulo = "  ";
        final var expectedArtistaId = UUID.randomUUID();
        final var expectedArtistasIds = Set.of(expectedArtistaId);
        final var expectedErrorMessage = "'albumTitulo' should not be empty";

        final var aCommand = CreateAlbumCommand.with(expectedTitulo, expectedArtistasIds);

        when(artistaGateway.existsBySecureIds(any()))
                .thenReturn(List.of(ArtistaID.from(1L, expectedArtistaId.toString())));

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(albumGateway, never()).create(any());
    }
}