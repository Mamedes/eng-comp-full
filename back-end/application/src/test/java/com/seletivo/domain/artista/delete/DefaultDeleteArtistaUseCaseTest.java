package com.seletivo.domain.artista.delete;

import com.seletivo.application.artista.delete.DefaultDeleteArtistaUseCase;
import com.seletivo.domain.artista.ArtistaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDeleteArtistaUseCaseTest {

    @InjectMocks
    private DefaultDeleteArtistaUseCase useCase;

    @Mock
    private ArtistaGateway artistaGateway;

    @Test
    void givenAValidId_whenCallsDeleteArtista_thenShouldBeOK() {
        final var expectedId = UUID.randomUUID();

        doNothing().when(artistaGateway).deleteBySecureId(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId));

        verify(artistaGateway, times(1)).deleteBySecureId(eq(expectedId));
    }


}