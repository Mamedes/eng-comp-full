package com.seletivo.domain.artista.create;

import com.seletivo.application.artista.create.CreateArtistaCommand;
import com.seletivo.application.artista.create.DefaultCreateArtistaUseCase;
import com.seletivo.domain.artista.ArtistaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultCreateArtistaUseCaseTest {

    @InjectMocks
    private DefaultCreateArtistaUseCase useCase;

    @Mock
    private ArtistaGateway artistaGateway;

    @Test
    void givenAValidCommand_whenCallsCreateArtista_thenShouldReturnArtistaId() {
        final var expectedNome = "Legião Urbana";
        final var expectedTipo = "Banda";

        final var aCommand = new CreateArtistaCommand(expectedNome, expectedTipo);

        when(artistaGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.artistaId());

        verify(artistaGateway).create(argThat(artista ->
                Objects.equals(expectedNome, artista.getNome())
                        && Objects.equals(expectedTipo, artista.getTipo())
                        && Objects.nonNull(artista.getSecureId())
                        && Objects.nonNull(artista.getCreatedAt())
        ));
    }

    @Test
    void givenAInvalidName_whenCallsCreateArtista_thenShouldReturnDomainException() {
        final String expectedNome = null;
        final var expectedTipo = "Banda";
        final var expectedErrorMessage = "'nome' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand = new CreateArtistaCommand(expectedNome, expectedTipo);

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(artistaGateway, times(0)).create(any());
    }

}