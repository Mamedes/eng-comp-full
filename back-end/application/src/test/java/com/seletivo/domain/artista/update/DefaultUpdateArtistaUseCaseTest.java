package com.seletivo.domain.artista.update;

import com.seletivo.application.artista.update.DefaultUpdateArtistaUseCase;
import com.seletivo.application.artista.update.UpdateArtistaCommand;
import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUpdateArtistaUseCaseTest {

    @InjectMocks
    private DefaultUpdateArtistaUseCase useCase;

    @Mock
    private ArtistaGateway artistaGateway;

    @Test
    void givenAValidCommand_whenCallsUpdateArtista_thenShouldReturnOutput() {
        final var aArtista = Artista.newArtista("Djavan", "Cantor");
        final var expectedSecureId = aArtista.getSecureId();
        final var expectedNome = "Djavan Atualizado";
        final var expectedTipo = "Compositor";

        final var previousUpdatedAt = aArtista.getUpdatedAt();
        final var aCommand =
                new UpdateArtistaCommand(expectedSecureId, expectedNome, expectedTipo);

        when(artistaGateway.findBySecureId(eq(expectedSecureId)))
                .thenReturn(Optional.of(aArtista));

        when(artistaGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertEquals(expectedSecureId, actualOutput.secureId());

        verify(artistaGateway).findBySecureId(eq(expectedSecureId));
        verify(artistaGateway).update(argThat(updated ->
                Objects.equals(expectedNome, updated.getNome())
                        && Objects.equals(expectedTipo, updated.getTipo())
                        && Objects.equals(expectedSecureId, updated.getSecureId())
                        && !updated.getUpdatedAt().isBefore(previousUpdatedAt)
        ));
    }

    @Test
    void givenAInvalidName_whenCallsUpdateArtista_thenShouldReturnDomainException() {
        final var aArtista = Artista.newArtista("Djavan", "Cantor");
        final var expectedSecureId = aArtista.getSecureId();
        final String expectedNome = null;
        final var expectedTipo = "Compositor";
        final var expectedErrorMessage = "'nome' should not be empty";

        final var aCommand = new UpdateArtistaCommand(expectedSecureId, expectedNome, expectedTipo);

        when(artistaGateway.findBySecureId(eq(expectedSecureId)))
                .thenReturn(Optional.of(aArtista));

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(artistaGateway, times(0)).update(any());
    }

    @Test
    void givenAValidCommand_whenUpdateWithInvalidId_thenShouldThrowNotFoundException() {
        final var expectedSecureId = UUID.randomUUID();
        final var aCommand = new UpdateArtistaCommand(
                expectedSecureId,
                "Djavan",
                "Cantor"
        );

        when(artistaGateway.findBySecureId(expectedSecureId))
                .thenReturn(Optional.empty());

        final var exception = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(aCommand)
        );

        assertTrue(exception.getMessage().contains(expectedSecureId.toString()));

        verify(artistaGateway, never()).update(any());
    }

}