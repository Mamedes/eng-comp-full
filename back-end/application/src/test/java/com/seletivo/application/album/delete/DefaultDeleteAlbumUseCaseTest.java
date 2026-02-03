package com.seletivo.application.album.delete;

import com.seletivo.domain.album.AlbumGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDeleteAlbumUseCaseTest {

    @InjectMocks
    private DefaultDeleteAlbumUseCase useCase;

    @Mock
    private AlbumGateway albumGateway;

    @Test
    void givenAValidId_whenCallsDeleteAlbum_thenShouldBeOK() {
        final var expectedId = UUID.randomUUID();

        doNothing().when(albumGateway).deleteBySecureId(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId));

        verify(albumGateway, times(1)).deleteBySecureId(eq(expectedId));
    }

    @Test
    void givenAnInvalidId_whenCallsDeleteAlbum_thenShouldBeOK() {
        final var expectedId = UUID.randomUUID();

        doNothing().when(albumGateway).deleteBySecureId(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId));

        verify(albumGateway, times(1)).deleteBySecureId(eq(expectedId));
    }

    @Test
    void givenAValidId_whenGatewayThrowsException_thenShouldThrowException() {
        final var expectedId = UUID.randomUUID();
        final var expectedErrorMessage = "Gateway error";

        doThrow(new RuntimeException(expectedErrorMessage))
                .when(albumGateway).deleteBySecureId(eq(expectedId));

        try {
            useCase.execute(expectedId);
        } catch (Exception e) {
            assert(e.getMessage().equals(expectedErrorMessage));
        }

        verify(albumGateway, times(1)).deleteBySecureId(eq(expectedId));
    }
}